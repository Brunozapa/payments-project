package com.example.services.transaction.domain.entity

import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.entity.enums.TransactionType
import com.example.services.transaction.domain.event.TransactionCreatedEvent
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class TransactionTest {

    @Test
    fun `should create a transaction with PENDING status`() {
        val transaction = Transaction.create(
            accountId = "account-id",
            amount = BigDecimal("100.00"),
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT,
            receiverName = "Maria"
        )

        assertNotNull(transaction.id)
        assertEquals("account-id", transaction.accountId)
        assertEquals(BigDecimal("100.00"), transaction.amount)
        assertEquals(TransactionType.PAYMENT, transaction.type)
        assertEquals(TransactionStatus.PENDING, transaction.status)
        assertEquals("Maria", transaction.receiverName)
        assertTrue(transaction.getEvents().first() is TransactionCreatedEvent)
    }

    @Test
    fun `should update status and details`() {
        val transaction = Transaction.create(
            accountId = "account-id",
            amount = BigDecimal("100.00"),
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT
        )

        transaction.updateStatus(TransactionStatus.COMPLETED)

        assertEquals(TransactionStatus.COMPLETED, transaction.status)
    }

    @Test
    fun `should clear events after getEvents is called`() {
        val transaction = Transaction.create(
            accountId = "account-id",
            amount = BigDecimal("100.00"),
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT
        )

        val events = transaction.getEvents()

        assertEquals(0, transaction.getEvents().size)
        assertEquals(1, events.size)
    }
}