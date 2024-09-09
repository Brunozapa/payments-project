package com.example.services.account.domain.exception

class AccountNotFoundException(id: String) : BusinessException(
    msg = "Conta com $id não encontrada",
    type = "ACCOUNT_NOT_FOUND"
)