package com.example.services.account.domain.entity

import com.example.services.account.common.DateUtils.getAge
import com.example.services.account.domain.exception.InvalidBirthdateException
import java.time.LocalDate

data class Birthdate(val value: LocalDate) {
    init {
        if (value.getAge() < MIN_LEGAL_AGE) throw InvalidBirthdateException(
            "Contas não são permitidas para menores de 18 anos"
        )
    }

    companion object {
        const val MIN_LEGAL_AGE = 18
    }
}
