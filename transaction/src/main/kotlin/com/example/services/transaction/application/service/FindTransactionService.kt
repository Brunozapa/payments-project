package com.example.services.transaction.application.service

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.exception.TransactionNotFoundException
import com.example.services.transaction.domain.repository.TransactionRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class FindTransactionService(
    private val repository: TransactionRepository
) {
    fun findById(id: String): Transaction {
        logger.info { "Trying to find transaction by id $id" }
        return repository.findById(id) ?: throw TransactionNotFoundException("id: $id")
    }

    companion object : KLogging()
}