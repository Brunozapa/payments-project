package com.example.services.transaction.domain.repository

import com.example.services.transaction.domain.entity.Transaction

interface TransactionRepository {
    fun save(transaction: Transaction): Transaction
    fun findById(id: String): Transaction?
}