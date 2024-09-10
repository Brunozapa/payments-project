package com.example.services.transaction.resources.producer

import com.example.services.transaction.domain.entity.Transaction
import mu.KLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TransactionEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, TransactionEventMessage>
) {
    fun sendMessage(transaction: Transaction) {
        try {
            val message = TransactionEventMessage.of(transaction)
            logger.info { "Sending transaction to topic $TOPIC_TRANSACTION_EVENT with message: $message" }
            kafkaTemplate.send(TOPIC_TRANSACTION_EVENT, message)
        } catch (ex: Exception) {
            logger.error(ex) { "Could not send transaction event message to topic. Error: ${ex.message}" }
            throw ex
        }
    }

    companion object : KLogging() {
        const val TOPIC_TRANSACTION_EVENT = "TOPIC.TRANSACTION.EVENT"
    }
}