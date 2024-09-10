package com.example.services.account.domain.entity

import com.example.services.account.domain.entity.enums.OperationType
import com.example.services.account.domain.exception.InvalidTransactionAmountException
import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal

class Account private constructor(
    val id: String = ULID().nextULID(),
    val cpf: String,
    val name: String,
    val birthdate: Birthdate,
    phone: String?,
    email: String?,
    balance: Balance
) {

    var phone: String? = phone
        private set

    var email: String? = email
        private set

    var balance: Balance = balance
        private set

    fun debit(amount: BigDecimal): Account {
        if (amount <= BigDecimal.ZERO)
            throw InvalidTransactionAmountException("O valor do débito deve ser maior que zero.")

        balance = this.balance.subtract(amount)
        return this
    }

    fun credit(amount: BigDecimal): Account {
        if (amount <= BigDecimal.ZERO)
            throw InvalidTransactionAmountException("O valor do crédito deve ser maior que zero.")

        balance = this.balance.add(amount)
        return this
    }

    companion object {

        fun create(accountToCreate: AccountToCreate): Account {
            return Account(
                name = accountToCreate.name,
                cpf = accountToCreate.cpf,
                birthdate = accountToCreate.birthdate,
                phone = accountToCreate.phone,
                email = accountToCreate.email,
                balance = Balance(BigDecimal.ZERO)
            )
        }

        fun of(
            id: String,
            cpf: String,
            name: String,
            birthdate: Birthdate,
            phone: String?,
            email: String?,
            balance: Balance
        ) = Account(id, cpf, name, birthdate, phone, email, balance)
    }
}