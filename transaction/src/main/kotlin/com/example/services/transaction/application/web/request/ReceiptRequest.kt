package com.example.services.transaction.application.web.request

import java.math.BigDecimal

data class ReceiptRequest(
    val accountId: String,
    val amount: BigDecimal,
    val payerName: String
)
