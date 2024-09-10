package com.example.services.transaction.application.web.request

import java.math.BigDecimal

data class PaymentRequest(
    val accountId: String,
    val amount: BigDecimal,
    val receiverName: String
)
