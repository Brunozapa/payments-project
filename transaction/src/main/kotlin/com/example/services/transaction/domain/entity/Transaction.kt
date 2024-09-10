package com.example.services.transaction.domain.entity

import com.example.services.transaction.common.DomainEvent
import com.example.services.transaction.domain.entity.enums.TransactionStatus
import com.example.services.transaction.domain.entity.enums.TransactionType
import com.example.services.transaction.domain.event.TransactionCreatedEvent
import de.huxhorn.sulky.ulid.ULID
import java.math.BigDecimal
import java.time.LocalDateTime

class Transaction private constructor(
    val id: String = ULID().nextULID(),
    val accountId: String,
    val amount: BigDecimal,
    val type: TransactionType,
    val transactionDate: LocalDateTime,
    payerName: String?,
    receiverName: String?,
    status: TransactionStatus,
    statusDetails: String?
) {

    var payerName: String? = payerName
        private set

    var receiverName: String? = receiverName
        private set

    var status: TransactionStatus = status
        private set

    var statusDetails: String? = statusDetails
        private set


    fun updateStatus(newStatus: TransactionStatus, details: String?): Transaction {
        this.status = newStatus
        this.statusDetails = details

        return this
    }

    companion object {
        fun create(
            accountId: String,
            amount: BigDecimal,
            transactionDate: LocalDateTime,
            type: TransactionType,
            payerName: String? = null,
            receiverName: String? = null,
            statusDetails: String? = null
        ): Transaction {
            val transaction = Transaction(
                accountId = accountId,
                amount = amount,
                status = TransactionStatus.PENDING,
                type = type,
                transactionDate = transactionDate,
                payerName = payerName,
                receiverName = receiverName,
                statusDetails = statusDetails
            )
            val event = TransactionCreatedEvent(transaction)
            transaction.addEvent(event)
            return transaction
        }

        fun of(
            id: String,
            accountId: String,
            amount: BigDecimal,
            status: TransactionStatus,
            type: TransactionType,
            transactionDate: LocalDateTime,
            payerName: String?,
            receiverName: String?,
            statusDetails: String?
        ) = Transaction(
            id = id,
            accountId = accountId,
            amount = amount,
            status = status,
            type = type,
            transactionDate = transactionDate,
            payerName = payerName,
            receiverName = receiverName,
            statusDetails = statusDetails
        )
    }

    private var events: MutableList<DomainEvent> = mutableListOf()

    fun getEvents(): MutableList<DomainEvent> = this.events.also { this.events = mutableListOf() }

    private fun addEvent(event: DomainEvent) { events.add(event) }
}