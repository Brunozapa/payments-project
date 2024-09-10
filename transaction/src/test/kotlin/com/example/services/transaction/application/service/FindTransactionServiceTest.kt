package com.example.services.transaction.application.service

import com.example.services.transaction.domain.exception.TransactionNotFoundException
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.factory.TransactionFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindTransactionServiceTest {
    private val repository = mockk<TransactionRepository>()
    private val findTransactionService = FindTransactionService(repository)

    @Test
    fun `should find transaction by id`() {
        val transactionId = "transactionId"
        val transaction = TransactionFactory.create(transactionId)

        every { repository.findById(transactionId) } returns transaction

        val result = findTransactionService.findById(transactionId)

        verify { repository.findById(transactionId) }
        assertEquals(transaction, result)
    }

    @Test
    fun `should throw TransactionNotFoundException when transaction not found`() {
        val transactionId = "transactionId"

        every { repository.findById(transactionId) } returns null

        assertThrows<TransactionNotFoundException> {
            findTransactionService.findById(transactionId)
        }

        verify { repository.findById(transactionId) }
    }
}