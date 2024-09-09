package com.example.services.account.application.service

import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountCreateException
import com.example.services.account.domain.repository.AccountRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CreateAccountService(
    private val accountRepository: AccountRepository
) {
    fun execute(request: CreateAccountRequest): Account {
        logger.info { "Trying to create account for CPF: ${request.cpf}." }

        val account = Account.create(request.toDomain())

        accountRepository.findByCpf(account.cpf)?.let {
            val exception = AccountCreateException("O CPF ${account.cpf} já possui uma conta.")
            logger.error(exception) { "Account creation failed: An account with CPF ${account.cpf} already exists."}
            throw exception
        }

        return accountRepository.save(account).also {
            logger.info { "Account successfully created for CPF: ${account.cpf} with id: ${it.id}" }
        }
    }

    companion object : KLogging()
}
