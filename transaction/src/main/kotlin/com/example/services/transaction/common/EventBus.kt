package com.example.services.transaction.common

interface EventBus {
    fun send(event: DomainEvent)
}
