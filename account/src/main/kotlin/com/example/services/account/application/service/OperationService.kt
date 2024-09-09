package com.example.services.account.application.service

import com.example.services.account.domain.entity.Account
import com.example.services.account.domain.exception.AccountNotFoundException
import com.example.services.account.domain.repository.AccountRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OperationService(
    private val accountRepository: AccountRepository
) {

    fun debit(id: String, amount: BigDecimal) {
        logger.info { "Trying to execute a debit operation of $amount for account with id $id" }
        processTransaction(id, amount) { account, amountToDebit ->
            account.debit(amountToDebit)
        }
    }

    fun credit(id: String, amount: BigDecimal) {
        logger.info { "Trying to execute a debit operation of $amount for account with id: $id" }
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
            logger.info { "Acquiring lock for accountId: $id" }
            accountRepository.advisoryLockById(id)

            val account = accountRepository.findById(id) ?: run {
                logger.error { "Account not found for id: $id" }
                throw AccountNotFoundException("id: $id")
            }

            val updatedAccount = operation(account, amount)
            accountRepository.save(updatedAccount)
            logger.info { "Transaction processed successfully for accountId $id with amount: $amount" }

        } catch (e: Exception) {
            logger.error(e) { "Error processing transaction for accountId $id with amount: $amount" }
            throw e
        } finally {
            logger.info { "Releasing lock for accountId: $id" }
            accountRepository.advisoryUnlockById(id)
        }
    }

    companion object : KLogging()
}