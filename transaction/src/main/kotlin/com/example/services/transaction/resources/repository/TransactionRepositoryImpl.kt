package com.example.services.transaction.resources.repository

import com.example.services.transaction.common.EventBus
import com.example.services.transaction.domain.entity.Transaction
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.resources.repository.entity.TransactionEntity
import org.springframework.stereotype.Component


@Component
class TransactionRepositoryImpl(
    private val repository: TransactionRepositoryMongo,
    private val eventBus: EventBus
) : TransactionRepository {
    override fun save(transaction: Transaction): Transaction {
        return repository.save(TransactionEntity.of(transaction)).also{
            sendDomainEvents(transaction)
        }.toDomain()
    }

    override fun findById(id: String): Transaction? {
        return repository.findById(id).takeIf { it.isPresent }?.get()?.toDomain()
    }

    private fun sendDomainEvents(transaction: Transaction) = transaction.getEvents().forEach { eventBus.send(it) }

}