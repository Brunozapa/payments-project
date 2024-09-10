package com.example.services.transaction.resources

import com.example.services.transaction.common.DomainEvent
import com.example.services.transaction.common.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventBusImpl(private val eventPublisher: ApplicationEventPublisher) : EventBus {
    override fun send(event: DomainEvent) {
        eventPublisher.publishEvent(event)
    }
}