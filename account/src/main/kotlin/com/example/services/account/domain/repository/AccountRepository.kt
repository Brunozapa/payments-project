package com.example.services.account.domain.repository

import com.example.services.account.domain.entity.Account

interface AccountRepository {
    fun save(account: Account): Account
    fun findByCpf(cpf: String): Account?
}