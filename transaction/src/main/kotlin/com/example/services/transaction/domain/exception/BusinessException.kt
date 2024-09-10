package com.example.services.transaction.domain.exception

open class BusinessException(val msg: String, val type: String) : Exception(msg)
