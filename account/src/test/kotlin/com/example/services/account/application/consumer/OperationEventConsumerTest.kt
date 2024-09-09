package com.example.services.account.application.consumer

import com.example.services.account.application.consumer.message.OperationEventMessage
import com.example.services.account.application.service.OperationService
import com.example.services.account.domain.entity.enums.OperationType
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class OperationEventConsumerTest {
    private val operationService = mockk<OperationService>()
    private val objectMapper = mockk<ObjectMapper>()

    private val consumer = OperationEventConsumer(operationService, objectMapper)

    @Test
    fun `should process credit operation message successfully`() {
        val message = "{\"operationType\":\"CREDIT\",\"accountId\":\"1\",\"amount\":100.00}"
        val operationEvent = OperationEventMessage(
            operationType = OperationType.CREDIT,
            accountId = "1",
            amount = BigDecimal("100.00")
        )
        every { objectMapper.readValue(message, OperationEventMessage::class.java) } returns operationEvent
        every { operationService.credit(any(), any()) } just Runs

        assertDoesNotThrow { consumer.consume(message) }
        verify { objectMapper.readValue(message, OperationEventMessage::class.java) }
        verify { operationService.credit(operationEvent.accountId, operationEvent.amount) }
        verify(exactly = 0) { operationService.debit(operationEvent.accountId, operationEvent.amount) }

    }

    @Test
    fun `should process debit operation message successfully`() {
        val message = "{\"operationType\":\"DEBIT\",\"accountId\":\"1\",\"amount\":100.00}"
        val operationEvent = OperationEventMessage(
            operationType = OperationType.DEBIT,
            accountId = "1",
            amount = BigDecimal("100.00")
        )
        every { objectMapper.readValue(message, OperationEventMessage::class.java) } returns operationEvent
        every { operationService.debit(any(), any()) } just Runs

        assertDoesNotThrow { consumer.consume(message) }
        verify { objectMapper.readValue(message, OperationEventMessage::class.java) }
        verify { operationService.debit(operationEvent.accountId, operationEvent.amount) }
        verify(exactly = 0) { operationService.credit(operationEvent.accountId, operationEvent.amount) }
    }

    @Test
    fun `should handle exception when processing message`() {
        val message = "{\"operationType\":\"CREDIT\",\"accountId\":\"1\",\"amount\":100.00}"
        every { objectMapper.readValue(message, OperationEventMessage::class.java) } throws RuntimeException("Error parsing message")

        assertThrows<RuntimeException> { consumer.consume(message) }
        verify { objectMapper.readValue(message, OperationEventMessage::class.java) }
        verify(exactly = 0) { operationService.credit(any(), any()) }
    }
}