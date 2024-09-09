package com.example.services.account.application.service

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountNotFoundException
import com.example.services.account.domain.repository.AccountRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OperationService(
    private val accountRepository: AccountRepository
) {

    fun debit(id: String, amount: BigDecimal) {
        processTransaction(id, amount) { account, amountToDebit ->
            account.debit(amountToDebit)
        }
    }

    fun credit(id: String, amount: BigDecimal) {
        processTransaction(id, amount) { account, amountToCredit ->
            account.credit(amountToCredit)
        }
    }

    private fun processTransaction(
        id: String,
        amount: BigDecimal,
        operation: (Account, BigDecimal) -> Account
    ) {
        try {
            accountRepository.advisoryLockById(id)

            accountRepository.findById(id)?.let { account ->
                accountRepository.save(operation(account, amount))
            }?: throw AccountNotFoundException("id: $id")

        } finally {
            accountRepository.advisoryUnlockById(id)
        }
    }
}