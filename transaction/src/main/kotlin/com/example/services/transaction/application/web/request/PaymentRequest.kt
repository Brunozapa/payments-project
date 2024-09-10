package com.example.services.transaction.application.web.request

import com.example.services.transaction.domain.entity.enums.TransactionType
import java.math.BigDecimal

data class PaymentRequest(
    val type: TransactionType,
    val accountId: String,
    val amount: BigDecimal,
    val receiverName: String
)
