package com.example.services.account.application.consumer

import com.example.services.account.application.consumer.message.OperationEventMessage
import com.example.services.account.application.service.OperationService
import com.example.services.account.domain.entity.enums.OperationType
import com.example.services.account.domain.exception.AccountLockedException
import com.example.services.account.domain.exception.BusinessException
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component


@Component
class OperationEventConsumer(
    private val operationService: OperationService,
    private val objectMapper: ObjectMapper
) {
    @KafkaListener(topics = [TOPIC_TRANSACTION_EVENT])
    @RetryableTopic(attempts = "3", backoff = Backoff(delay = 2000), include = [AccountLockedException::class])
    fun consume(message: String) {
        objectMapper.readValue(message, OperationEventMessage::class.java).let {
            when (it.operationType) {
                OperationType.CREDIT -> operationService.credit(it.accountId, it.amount)
                OperationType.DEBIT -> operationService.debit(it.accountId, it.amount)
            }
        }
    }

    @KafkaListener(topics = [TOPIC_TRANSACTION_EVENT])
    @RetryableTopic(attempts = "3", backoff = Backoff(delay = 2000), include = [AccountLockedException::class])
    fun consume1(message: String) {
        try {
            logger.info { "Received message: $message" }

            val operationEvent = objectMapper.readValue(message, OperationEventMessage::class.java)
            when (operationEvent.operationType) {
                OperationType.CREDIT -> {
                    operationService.credit(operationEvent.accountId, operationEvent.amount)
                }
                OperationType.DEBIT -> {
                    operationService.debit(operationEvent.accountId, operationEvent.amount)
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "Error processing message: $message" }
            throw e
        }
    }

    companion object : KLogging() {
        const val TOPIC_TRANSACTION_EVENT = "TOPIC.OPERATION.EVENT"
    }
}