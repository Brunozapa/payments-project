package com.example.services.account.domain.exception

class InvalidTransactionAmountException(message: String) : BusinessException(
    msg = message,
    type = "INVALID_TRANSCTION_AMOUNT_EXCEPTION"
)