package com.example.services.transaction.application.service

import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.exception.TransactionNotFoundException
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.factory.TransactionFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class UpdateTransactionStatusServiceTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val updateTransactionStatusService = UpdateTransactionStatusService(transactionRepository)

    @Test
    fun `should update transaction status successfully`() {
        val transactionId = "transactionId"
        val status = TransactionStatus.COMPLETED
        val details = "Completed"

        val originalTransaction = TransactionFactory.create()
        val updatedTransaction = originalTransaction.updateStatus(status, details)

        every { transactionRepository.findById(transactionId) } returns originalTransaction
        every { transactionRepository.save(updatedTransaction) } returns updatedTransaction

        val result = updateTransactionStatusService.execute(transactionId, status, details)

        verify { transactionRepository.findById(transactionId) }
        verify { transactionRepository.save(updatedTransaction) }
        assertEquals(updatedTransaction, result)
    }

    @Test
    fun `should throw TransactionNotFoundException when transaction not found`() {
        val transactionId = "transactionId"
        val status = TransactionStatus.COMPLETED
        val details = "Completed"

        every { transactionRepository.findById(transactionId) } returns null

        assertThrows<TransactionNotFoundException> {
            updateTransactionStatusService.execute(transactionId, status, details)
        }

        verify { transactionRepository.findById(transactionId) }
    }
}
