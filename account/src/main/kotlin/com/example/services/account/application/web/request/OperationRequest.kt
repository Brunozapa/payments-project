package com.example.services.account.application.web.request

import java.math.BigDecimal

data class OperationRequest(
    val amount: BigDecimal
)