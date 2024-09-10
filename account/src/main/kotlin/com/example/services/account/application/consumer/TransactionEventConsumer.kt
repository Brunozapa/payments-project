package com.example.services.account.application.consumer

import com.example.services.account.application.dto.TransactionEventMessage
import com.example.services.account.application.dto.TransactionType
import com.example.services.account.application.service.OperationService
import com.example.services.account.domain.exception.AccountLockedException
import com.example.services.account.domain.exception.BusinessException
import com.example.services.account.application.dto.TransactionStatus
import com.example.services.account.application.dto.TransactionResultMessage
import com.example.services.account.resources.producer.TransactionResultProducer
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component


@Component
class TransactionEventConsumer(
    private val operationService: OperationService,
    private val transactionResultProducer: TransactionResultProducer,
    private val objectMapper: ObjectMapper
) {
    @KafkaListener(topics = [TOPIC_TRANSACTION_EVENT])
    @RetryableTopic(attempts = "3", backoff = Backoff(delay = 2000), include = [AccountLockedException::class])
    fun consume(message: String) {
        try {
            logger.info { "Received message: $message" }
            val transactionEvent = readValue(message)
            try {
                when (transactionEvent.transactionType) {
                    TransactionType.RECEIPT -> {
                        operationService.credit(transactionEvent.accountId, transactionEvent.amount)
                    }

                    TransactionType.PAYMENT -> {
                        operationService.debit(transactionEvent.accountId, transactionEvent.amount)
                    }
                }
                sendResultMessage(transactionEvent.transactionId, TransactionStatus.COMPLETED)
            } catch (be: BusinessException) {
                sendResultMessage(transactionEvent.transactionId, TransactionStatus.REFUSED, be.msg)
            } catch (ex: Exception) {
                sendResultMessage(transactionEvent.transactionId, TransactionStatus.FAILED)
            }
        } catch (e: Exception) {
            logger.error(e) { "Error trying to process transaction event: $message" }
            throw e
        }
    }

    private fun sendResultMessage(
        transactionId: String,
        status: TransactionStatus,
        statusDetails: String? = null
    ) {
        transactionResultProducer.sendMessage(
            TransactionResultMessage(transactionId, status, statusDetails)
        )
    }

    private fun readValue(message: String): TransactionEventMessage {
        try {
            return objectMapper.readValue(message, TransactionEventMessage::class.java)
        } catch (e: Exception) {
            logger.error(e) { "Error reading message: $message" }
            throw e
        }
    }


    companion object : KLogging() {
        const val TOPIC_TRANSACTION_EVENT = "TOPIC.TRANSACTION.EVENT"
    }
}