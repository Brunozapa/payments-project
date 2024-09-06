package com.example.services.account.domain.exception

open class BusinessException(val msg: String, val type: String) : Exception(msg)
