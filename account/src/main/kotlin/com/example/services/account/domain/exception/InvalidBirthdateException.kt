package com.example.services.account.domain.exception

class InvalidBirthdateException(message: String) : BusinessException(
    msg = message,
    type = "INVALID_BIRTHDATE_EXCEPTION"
)
