package com.example.services.account.factory

import com.example.services.account.domain.entity.AccountToCreate
import com.example.services.account.domain.entity.Birthdate
import java.time.LocalDate

object AccountToCreateFactory {

    fun create() = AccountToCreate(
        name = "João",
        cpf = "123456789",
        birthdate = Birthdate(LocalDate.of(2000, 12, 25)),
        phone = "1234567889",
        email = "email@teste"
    )

    fun createWithoutNullables() = AccountToCreate(
        name = "João",
        cpf = "123456789",
        birthdate = Birthdate(LocalDate.of(2000, 12, 25)),
        phone = null,
        email = null
    )
}