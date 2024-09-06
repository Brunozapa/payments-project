package com.example.services.account.domain.entity

import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal

data class Account(
    val id: String = ULID().nextULID(),

    val name: String,
    val cpf: String,
    val birthdate: Birthdate,
    val phone: String?,
    val email: String?,

    val amount: Amount
) {

    companion object {
        const val MIN_LEGAL_AGE = 18

        fun create(accountToCreate: AccountToCreate): Account {
            return Account(
                name = accountToCreate.name,
                cpf = accountToCreate.cpf,
                birthdate = accountToCreate.birthdate,
                phone = accountToCreate.phone,
                email = accountToCreate.email,

                amount = Amount(BigDecimal.ZERO)
            )
        }
    }


}