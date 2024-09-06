package com.example.services.account.common

import java.time.LocalDate
import java.time.Period

object DateUtils {
    fun LocalDate.getAge(): Int = Period.between(this, LocalDate.now()).years
}