package com.example.services.transaction.application.consumer

import com.example.services.transaction.application.service.UpdateTransactionStatusService
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TransactionResultConsumer(
    private val objectMapper: ObjectMapper,
    private val updateTransactionStatusService: UpdateTransactionStatusService
) {

    @KafkaListener(topics = [TOPIC_TRANSACTION_RESULT])
    fun consume(message: String) {
        try{
            logger.info { "Received message: $message" }
            objectMapper.readValue(message, TransactionResultMessage::class.java).let {
                updateTransactionStatusService.execute(it.transactionId, it.status, it.statusDetails)
            }
        } catch (e: Exception) {
            logger.error(e) { "Error processing message: $message" }
            throw e
        }
    }

    companion object : KLogging() {
        const val TOPIC_TRANSACTION_RESULT = "TOPIC.TRANSACTION.RESULT"
    }
}