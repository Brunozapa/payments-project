package com.example.services.transaction.application.service

import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.exception.TransactionNotFoundException
import com.example.services.transaction.domain.repository.TransactionRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class UpdateTransactionStatusService(
    private val transactionRepository: TransactionRepository
) {
    fun execute(id: String, status: TransactionStatus, details: String?): Transaction {

        val transaction = transactionRepository.findById(id)?.updateStatus(status, details)
            ?: throw TransactionNotFoundException("id: $id")

        return transactionRepository.save(transaction)
    }

    companion object : KLogging()
}