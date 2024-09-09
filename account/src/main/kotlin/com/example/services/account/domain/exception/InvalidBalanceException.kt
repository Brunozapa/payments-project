package com.example.services.account.domain.exception

class InvalidBalanceException(message: String) : BusinessException(
    msg = message,
    type = "INVALID_BALANCE_EXCEPTION"
)
