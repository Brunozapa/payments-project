package com.example.services.account.application.service

import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountCreateException
import com.example.services.account.domain.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class CreateAccountService(
    private val accountRepository: AccountRepository
) {
    fun execute(request: CreateAccountRequest): Account {
        val account = Account.create(request.toDomain())

        return accountRepository.findByCpf(account.cpf)?.let {
            throw AccountCreateException("O CPF ${account.cpf} já possui uma conta")
        } ?: accountRepository.save(account)
    }
}
