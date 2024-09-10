package com.example.services.transaction.domain.event

import com.example.services.transaction.common.DomainEvent
import com.example.services.transaction.domain.entity.Transaction

class TransactionCreatedEvent(val transaction: Transaction) : DomainEvent