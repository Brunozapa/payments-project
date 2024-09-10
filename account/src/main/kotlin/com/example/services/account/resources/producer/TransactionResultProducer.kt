package com.example.services.account.resources.producer

import com.example.services.account.application.dto.TransactionResultMessage
import mu.KLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TransactionResultProducer(
    private val kafkaTemplate: KafkaTemplate<String, TransactionResultMessage>
) {
    fun sendMessage(message: TransactionResultMessage) {
        try{
            logger.info { "Sending transaction to topic $TOPIC_TRANSACTION_RESULT with message: $message" }
            kafkaTemplate.send(TOPIC_TRANSACTION_RESULT, message)
        } catch (ex: Exception) {
            logger.error(ex) { "Could not send transaction event message to topic. Error: ${ex.message}" }
            throw ex
        }
    }

    companion object : KLogging() {
        const val TOPIC_TRANSACTION_RESULT = "TOPIC.TRANSACTION.RESULT"
    }
}