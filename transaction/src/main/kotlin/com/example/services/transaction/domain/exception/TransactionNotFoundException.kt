package com.example.services.transaction.domain.exception

class TransactionNotFoundException(id: String) : BusinessException(
    msg = "Any transaction found for $id",
    type = "TRANSACTION_NOT_FOUND"
)
