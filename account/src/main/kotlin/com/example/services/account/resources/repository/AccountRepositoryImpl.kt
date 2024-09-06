package com.example.services.account.resources.repository

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.repository.AccountRepository
import org.springframework.stereotype.Component

@Component
class AccountRepositoryImpl(
    private val repository: AccountRepositoryJpa
) : AccountRepository {

    override fun save(account: Account): Account {
        return repository.save(AccountEntity.of(account)).toDomain()
    }

    override fun findByCpf(cpf: String): Account? {
        return repository.findByCpf(cpf)?.toDomain()
    }
}
