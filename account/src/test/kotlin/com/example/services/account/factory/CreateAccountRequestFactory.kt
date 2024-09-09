package com.example.services.account.factory

import com.example.services.account.application.web.request.CreateAccountRequest
import java.time.LocalDate

object CreateAccountRequestFactory {
    fun create() = CreateAccountRequest(
        name = "João",
        cpf = "123456789",
        birthdate = LocalDate.of(2000, 12, 25),
        phone = "1234567889",
        email = "email@teste"
    )
}