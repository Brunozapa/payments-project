package com.example.services.account.application.consumer

import com.example.services.account.application.dto.TransactionEventMessage
import com.example.services.account.application.dto.TransactionType
import com.example.services.account.application.service.OperationService
import com.example.services.account.resources.producer.TransactionResultProducer
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class TransactionEventConsumerTest {
    private val operationService = mockk<OperationService>()
    private val objectMapper = mockk<ObjectMapper>()
    private val transactionResultProducer = mockk<TransactionResultProducer>()

    private val consumer = TransactionEventConsumer(operationService, transactionResultProducer, objectMapper)

    @Test
    fun `should process credit operation message successfully`() {
        val message = "{\"transactionType\":\"CREDIT\",\"accountId\":\"1\",\"amount\":100.00}"
        val operationEvent = TransactionEventMessage(
            transactionId = "transactionId",
            transactionType = TransactionType.RECEIPT,
            accountId = "1",
            amount = BigDecimal("100.00")
        )
        every { objectMapper.readValue(message, TransactionEventMessage::class.java) } returns operationEvent
        every { operationService.credit(any(), any()) } just Runs
        every { transactionResultProducer.sendMessage(any()) } just Runs

        assertDoesNotThrow { consumer.consume(message) }
        verify { objectMapper.readValue(message, TransactionEventMessage::class.java) }
        verify { operationService.credit(operationEvent.accountId, operationEvent.amount) }
        verify(exactly = 0) { operationService.debit(operationEvent.accountId, operationEvent.amount) }

    }

    @Test
    fun `should process debit operation message successfully`() {
        val message = "{\"transactionType\":\"DEBIT\",\"accountId\":\"1\",\"amount\":100.00}"
        val operationEvent = TransactionEventMessage(
            transactionId = "transactionId",
            transactionType = TransactionType.PAYMENT,
            accountId = "1",
            amount = BigDecimal("100.00")
        )
        every { objectMapper.readValue(message, TransactionEventMessage::class.java) } returns operationEvent
        every { operationService.debit(any(), any()) } just Runs
        every { transactionResultProducer.sendMessage(any()) } just Runs

        assertDoesNotThrow { consumer.consume(message) }
        verify { objectMapper.readValue(message, TransactionEventMessage::class.java) }
        verify { operationService.debit(operationEvent.accountId, operationEvent.amount) }
        verify(exactly = 0) { operationService.credit(operationEvent.accountId, operationEvent.amount) }
    }

    @Test
    fun `should handle exception when processing message`() {
        val message = "{\"transactionType\":\"CREDIT\",\"accountId\":\"1\",\"amount\":100.00}"
        every { objectMapper.readValue(message, TransactionEventMessage::class.java) } throws RuntimeException("Error parsing message")

        assertThrows<RuntimeException> { consumer.consume(message) }
        verify { objectMapper.readValue(message, TransactionEventMessage::class.java) }
        verify(exactly = 0) { operationService.credit(any(), any()) }
        verify(exactly = 0) { transactionResultProducer.sendMessage(any()) }
    }
}