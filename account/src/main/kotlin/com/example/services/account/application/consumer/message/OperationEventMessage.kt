package com.example.services.account.application.consumer.message

import com.example.services.account.domain.entity.enums.OperationType
import java.math.BigDecimal

data class OperationEventMessage(
    val accountId: String,
    val operationType: OperationType,
    val amount: BigDecimal
)
