package com.example.services.account.domain.exception

class AccountCreateException(message: String) : BusinessException(
    msg = message,
    type = "CPF_ALREADY_REGISTERED"
)
