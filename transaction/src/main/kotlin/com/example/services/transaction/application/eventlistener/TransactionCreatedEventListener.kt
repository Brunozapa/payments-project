package com.example.services.transaction.application.eventlistener

import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.event.TransactionCreatedEvent
import com.example.services.transaction.domain.repository.TransactionRepository
import com.example.services.transaction.resources.producer.TransactionEventProducer
import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TransactionCreatedEventListener(
    private val transactionEventProducer: TransactionEventProducer,
    private val transactionRepository: TransactionRepository
) {
    @EventListener
    fun execute(event: TransactionCreatedEvent) {
        try {
            logger.info { "Created transaction event received for transactionId ${event.transaction.id}" }
            transactionEventProducer.sendMessage(event.transaction)
        } catch (ex: Exception) {
            val failMessage = "Fail to process transaction event."
            logger.error(ex) { "$failMessage $event" }
            event.transaction.updateStatus(TransactionStatus.FAILED, failMessage).also {
                transactionRepository.save(it)
            }

        }
    }

    companion object : KLogging()
}