package com.example.services.account.factory

import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.entity.Balance
import com.example.services.account.domain.entity.Birthdate
import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal
import java.time.LocalDate

object AccountFactory {

    fun create(
        amount: BigDecimal = BigDecimal.ZERO
    ) = Account.of(
        id = ULID().nextULID(),
        cpf = "123456789",
        name = "João",
        birthdate = Birthdate(LocalDate.of(2000, 12, 25)),
        phone = "1199912345",
        email = "email@teste.com",
        balance = Balance(amount)
    )

    fun create(
        request: CreateAccountRequest
    ) = Account.of(
        id = ULID().nextULID(),
        cpf = request.cpf,
        name = request.name,
        birthdate = Birthdate(request.birthdate),
        phone = request.phone,
        email = request.email,
        balance = Balance(BigDecimal.ZERO)
    )
}