package com.example.services.account.application.web.request

import com.example.services.account.domain.entity.AccountToCreate
import com.example.services.account.domain.entity.Birthdate
import java.time.LocalDate

data class CreateAccountRequest(
    val name: String,
    val cpf: String,
    val birthdate: LocalDate,
    val phone: String?,
    val email: String?
) {
    fun toDomain() = AccountToCreate(
        name = name,
        cpf = cpf,
        birthdate = Birthdate(birthdate),
        phone = phone,
        email = email
    )
}
