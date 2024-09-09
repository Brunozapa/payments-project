package com.example.services.account.resources.repository

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.entity.Balance
import com.example.services.account.domain.entity.Birthdate
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "account")
data class AccountEntity(
    @Id
    val id: String,
    val name: String,
    val cpf: String,
    val birthdate: LocalDate,
    val phone: String?,
    val email: String?,
    val balance: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
) {
    fun toDomain() = Account.of(
        id = id,
        name = name,
        cpf = cpf,
        birthdate = Birthdate(birthdate),
        phone = phone,
        email = email,
        balance = Balance(balance)
    )

    companion object {
        fun of(account: Account) = AccountEntity(
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
