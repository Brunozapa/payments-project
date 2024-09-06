package com.example.services.account.resources.repository

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.entity.Amount
import com.example.services.account.domain.entity.Birthdate
import jakarta.persistence.Column
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
    val amount: BigDecimal,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,

    @Column(insertable = false, updatable = false)
    val lockKey: Int? = null
) {
    fun toDomain() = Account(
        id = id,
        name = name,
        cpf = cpf,
        birthdate = Birthdate(birthdate),
        phone = phone,
        email = email,
        amount = Amount(amount)
    )

    companion object {
        fun of(account: Account) = AccountEntity(
            id = account.id,
            name = account.name,
            cpf = account.cpf,
            birthdate = account.birthdate.value,
            phone = account.phone,
            email = account.email,
            amount = account.amount.value
        )
    }
}
