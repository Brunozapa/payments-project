package com.example.services.transaction.application.eventlistener

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.entity.enums.TransactionType
import com.example.services.transaction.domain.event.TransactionCreatedEvent
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.resources.producer.TransactionEventProducer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class TransactionCreatedEventListenerTest {
    private val transactionEventProducer = mockk<TransactionEventProducer>()
    private val transactionRepository = mockk<TransactionRepository>()

    private val listener = TransactionCreatedEventListener(transactionEventProducer, transactionRepository)

    @Test
    fun `should send message on successful event handling`() {
        val transaction = Transaction.create(
            accountId = "account-id",
            amount = BigDecimal("100.00"),
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT
        )
        val event = TransactionCreatedEvent(transaction)

        every { transactionEventProducer.sendMessage(transaction) } returns Unit
        every { transactionRepository.save(transaction) } returns transaction

        listener.execute(event)

        verify { transactionEventProducer.sendMessage(transaction) }
        verify(exactly = 0) { transactionRepository.save(any()) }
    }

    @Test
    fun `should update status to FAILED and save transaction when an exception occurs`() {
        val transaction = Transaction.create(
            accountId = "account-id",
            amount = BigDecimal("100.00"),
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT
        )
        val event = TransactionCreatedEvent(transaction)
        val exception = RuntimeException("Test exception")

        every { transactionEventProducer.sendMessage(transaction) } throws exception
        every { transactionRepository.save(any()) } returns transaction

        listener.execute(event)

        verify { transactionEventProducer.sendMessage(transaction) }
        val updatedTransaction = transaction.updateStatus(TransactionStatus.FAILED)
        verify { transactionRepository.save(updatedTransaction) }

        assertEquals(TransactionStatus.FAILED, updatedTransaction.status)
        assertEquals(null, updatedTransaction.statusDetails)
    }
}