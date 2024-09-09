package com.example.services.account.application.service

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountNotFoundException
import com.example.services.account.domain.repository.AccountRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class FindAccountService(
    private val accountRepository: AccountRepository
) {
    fun findById(id: String): Account {
        logger.info { "Trying to find account with id: $id" }
        return accountRepository.findById(id) ?: throw AccountNotFoundException("id: $id")
    }

    companion object : KLogging()
}
