package com.example.services.transaction.factory

import com.example.services.transaction.application.web.request.PaymentRequest
import com.example.services.transaction.application.web.request.ReceiptRequest
import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.entity.enums.TransactionType
import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal
import java.time.LocalDateTime

object TransactionFactory {

    fun create(
        id: String = ULID().nextULID()
    ): Transaction {
        return Transaction.of(
            id = id,
            accountId = ULID().nextULID(),
            amount = BigDecimal("100.00"),
            type = TransactionType.PAYMENT,
            transactionDate = LocalDateTime.now(),
            payerName = "Pedro",
            receiverName = "Pedro",
            status = TransactionStatus.PENDING,
            statusDetails = null,
        )
    }

    fun create(
        paymentRequest: PaymentRequest
    ): Transaction {
        return Transaction.of(
            id = ULID().nextULID(),
            accountId = paymentRequest.accountId,
            amount = paymentRequest.amount,
            type = TransactionType.PAYMENT,
            transactionDate = LocalDateTime.now(),
            payerName = null,
            receiverName = paymentRequest.receiverName,
            status = TransactionStatus.PENDING,
            statusDetails = null,
        )
    }

    fun create(
        receiptRequest: ReceiptRequest
    ): Transaction {
        return Transaction.of(
            id = ULID().nextULID(),
            accountId = receiptRequest.accountId,
            amount = receiptRequest.amount,
            type = TransactionType.PAYMENT,
            transactionDate = LocalDateTime.now(),
            payerName = receiptRequest.payerName,
            receiverName = null,
            status = TransactionStatus.PENDING,
            statusDetails = null,
        )
    }
}
