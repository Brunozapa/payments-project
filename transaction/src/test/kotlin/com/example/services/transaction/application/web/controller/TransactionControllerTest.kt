package com.example.services.transaction.application.web.controller

import com.example.services.transaction.application.service.FindTransactionService
import com.example.services.transaction.application.service.StartTransactionService
import com.example.services.transaction.application.web.request.PaymentRequest
import com.example.services.transaction.application.web.request.ReceiptRequest
import com.example.services.transaction.application.web.response.TransactionResponse
import com.example.services.transaction.factory.TransactionFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal

class TransactionControllerTest {
    private val startTransactionService = mockk<StartTransactionService>()
    private val findTransactionService = mockk<FindTransactionService>()
    private val transactionController = TransactionController(startTransactionService, findTransactionService)

    @Test
    fun `should start payment and return CREATED response`() {
        val paymentRequest = PaymentRequest(
            accountId = "accountId",
            amount = BigDecimal("100.00"),
            receiverName = "Pedro"
        )
        val transaction = TransactionFactory.create(paymentRequest)

        every { startTransactionService.startPayment(paymentRequest) } returns transaction

        val response = transactionController.startPayment(paymentRequest)

        verify { startTransactionService.startPayment(paymentRequest) }
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(TransactionResponse.of(transaction), response.body)
    }

    @Test
    fun `should start receipt and return CREATED response`() {
        val receiptRequest = ReceiptRequest(
            accountId = "accountId",
            amount = BigDecimal("100.00"),
            payerName = "Pedro"
        )
        val transaction = TransactionFactory.create(receiptRequest)

        every { startTransactionService.startReceipt(receiptRequest) } returns transaction

        val response = transactionController.startReceipt(receiptRequest)

        verify { startTransactionService.startReceipt(receiptRequest) }
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(TransactionResponse.of(transaction), response.body)
    }

    @Test
    fun `should get transaction and return OK response`() {
        val transactionId = "transactionId"
        val transaction = TransactionFactory.create(transactionId)

        every { findTransactionService.findById(transactionId) } returns transaction

        val response = transactionController.getTransaction(transactionId)

        verify { findTransactionService.findById(transactionId) }
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(TransactionResponse.of(transaction), response.body)
    }
}