package com.example.services.transaction.application.consumer

import com.example.services.transaction.application.service.UpdateTransactionStatusService
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class TransactionResultConsumerTest {
    private val objectMapper = mockk<ObjectMapper>()
    private val updateTransactionStatusService = mockk<UpdateTransactionStatusService>(relaxed = true)
    private val transactionResultConsumer = TransactionResultConsumer(objectMapper, updateTransactionStatusService)

    @Test
    fun `should consume and process message successfully`() {
        val transactionId = "transactionId"
        val status = TransactionStatus.COMPLETED
        val details = "Success"

        val message = """{"transactionId":"$transactionId","status":"${status.name}","statusDetails":"$details"}"""
        val transactionResultMessage = TransactionResultMessage(transactionId, status, details)

        every { objectMapper.readValue(message, TransactionResultMessage::class.java) } returns transactionResultMessage

        transactionResultConsumer.consume(message)

        verifyOrder {
            objectMapper.readValue(message, TransactionResultMessage::class.java)
            updateTransactionStatusService.execute(transactionId, status, details)
        }
    }

    @Test
    fun `should log error and rethrow exception when processing fails`() {
        val transactionId = "transactionId"
        val status = TransactionStatus.COMPLETED
        val details = "Success"

        val message = """{"transactionId":"$transactionId","status":"${status.name}","statusDetails":"$details"}"""

        every { objectMapper.readValue(message, TransactionResultMessage::class.java) } throws RuntimeException()

        assertThrows<RuntimeException> {
            transactionResultConsumer.consume(message)
        }
        verify { objectMapper.readValue(message, TransactionResultMessage::class.java) }
        verify(exactly = 0) { updateTransactionStatusService.execute(any(), any(), any()) }
    }
}