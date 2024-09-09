package com.example.services.account.resources.repository

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountLockedException
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

    override fun findById(accountId: String): Account? {
        return repository.findById(accountId).takeIf { it.isPresent }?.get()?.toDomain()
    }

    override fun advisoryLockById(accountId: String) {
        val lockKey = accountId.hashCode().toLong()
        if (!repository.tryAdvisoryLock(lockKey)) throw AccountLockedException()
    }

    override fun advisoryUnlockById(accountId: String) {
        val lockKey = accountId.hashCode().toLong()
        repository.advisoryUnlock(lockKey)
    }
}
