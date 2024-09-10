package com.example.services.transaction.application.service

import com.example.services.transaction.application.web.request.PaymentRequest
import com.example.services.transaction.application.web.request.ReceiptRequest
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.factory.TransactionFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal
import kotlin.test.Test

class StartTransactionServiceTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val startTransactionService = StartTransactionService(transactionRepository)

    @Test
    fun `should create payment transaction successfully`() {
        val paymentRequest = PaymentRequest(
            accountId = "accountId",
            amount = BigDecimal("100.00"),
            receiverName = "Pedro"
        )

        val transaction = TransactionFactory.create(paymentRequest)

        every { transactionRepository.save(any()) } returns transaction

        val result = startTransactionService.startPayment(paymentRequest)

        verify { transactionRepository.save(any()) }
        assertEquals(transaction, result)
    }

    @Test
    fun `should create receipt transaction successfully`() {
        val receiptRequest = ReceiptRequest(
            accountId = "accountId",
            amount = BigDecimal("50.00"),
            payerName = "Pedro"
        )

        val transaction = TransactionFactory.create(receiptRequest)

        every { transactionRepository.save(any()) } returns transaction

        val result = startTransactionService.startReceipt(receiptRequest)

        verify { transactionRepository.save(any()) }
        assertEquals(transaction, result)
    }
}