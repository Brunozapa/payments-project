package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidAmountException
import java.math.BigDecimal

@JvmInline
value class Amount(val value: BigDecimal) {
    init {
        if (value < BigDecimal.ZERO) throw InvalidAmountException(
            "O saldo da conta deve ser maior que zero."
        )
    }
}