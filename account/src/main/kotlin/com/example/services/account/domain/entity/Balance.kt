package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidBalanceException
import java.math.BigDecimal

@JvmInline
value class Balance(val value: BigDecimal) {
    init {
        if (value < BigDecimal.ZERO) throw InvalidBalanceException(
            "O saldo da conta deve ser maior que zero."
        )
    }

    fun add(value: BigDecimal) = Balance(this.value + value)
    fun subtract(value: BigDecimal) = Balance(this.value - value)
}