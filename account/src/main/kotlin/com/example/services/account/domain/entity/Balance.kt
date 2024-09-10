package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidBalanceException
import java.math.BigDecimal

@JvmInline
value class Balance(val value: BigDecimal) {
    init {
        if (value < BigDecimal.ZERO) throw InvalidBalanceException(
            "O saldo da conta não pode ser negativo."
        )
    }

    fun add(addValue: BigDecimal) = Balance(this.value + addValue)
    fun subtract(subValue: BigDecimal) = Balance(this.value - subValue)
}