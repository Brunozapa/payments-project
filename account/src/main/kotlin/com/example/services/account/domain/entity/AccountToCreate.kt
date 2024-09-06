package com.example.services.account.domain.entity

data class AccountToCreate(
    val name: String,
    val cpf: String,
    val birthdate: Birthdate,
    val phone: String?,
    val email: String?,
)
