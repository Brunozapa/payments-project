package com.example.services.transaction.application.service

import com.example.services.transaction.application.web.request.PaymentRequest
import com.example.services.transaction.application.web.request.ReceiptRequest
import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionType
import com.example.services.transaction.domain.repository.TransactionRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StartTransactionService(
    private val transactionRepository: TransactionRepository
) {
    fun startPayment(paymentRequest: PaymentRequest): Transaction {
        logger.info { "Trying to create a payment transaction" }

        val transaction = Transaction.create(
            accountId = paymentRequest.accountId,
            amount = paymentRequest.amount,
            transactionDate = LocalDateTime.now(),
            type = TransactionType.PAYMENT,
            receiverName = paymentRequest.receiverName
        )

        return transactionRepository.save(transaction).also {
            logger.info { "Transaction created with success" }
        }
    }

    fun startReceipt(receiptRequest: ReceiptRequest): Transaction {
        logger.info { "Trying to create a receipt transaction" }

        val transaction = Transaction.create(
            accountId = receiptRequest.accountId,
            amount = receiptRequest.amount,
            transactionDate = LocalDateTime.now(),
            type = TransactionType.RECEIPT,
            payerName = receiptRequest.payerName
        )

        return transactionRepository.save(transaction).also {
            logger.info { "Transaction created with success" }
        }
    }

    companion object : KLogging()
}