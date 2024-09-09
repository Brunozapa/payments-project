package com.example.services.account.application.web.response

import com.example.services.account.domain.entity.Account
import java.math.BigDecimal
import java.time.LocalDate

data class AccountResponse(
    val id: String,
    val name: String,
    val cpf: String,
    val birthdate: LocalDate,
    val phone: String? = null,
    val email: String? = null,
    val balance: BigDecimal
) {
    companion object {
        fun of(account: Account) = AccountResponse(
            id = account.id,
            name = account.name,
            cpf = account.cpf,
            birthdate = account.birthdate.value,
            phone = account.phone,
            email = account.email,
            balance = account.balance.value
        )
    }
}