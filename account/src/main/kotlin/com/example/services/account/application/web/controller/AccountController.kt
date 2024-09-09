package com.example.services.account.application.web.controller

import com.example.services.account.application.consumer.message.OperationEventMessage
import com.example.services.account.application.service.CreateAccountService
import com.example.services.account.application.service.FindAccountService
import com.example.services.account.application.service.OperationService
import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.application.web.request.OperationRequest
import com.example.services.account.application.web.response.AccountResponse
import com.example.services.account.application.web.response.OperationResponse
import com.example.services.account.application.web.response.OperationStatus
import com.example.services.account.domain.entity.enums.OperationType
import com.example.services.account.domain.exception.BusinessException
import org.apache.kafka.clients.producer.KafkaProducer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/account")
class AccountController(
    private val createAccountService: CreateAccountService,
    private val operationService: OperationService,
    private val findAccountService: FindAccountService,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    @PostMapping
    fun create(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResponse> {
        val account = createAccountService.execute(request)
        return ResponseEntity(AccountResponse.of(account), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): ResponseEntity<AccountResponse> {
        val account = findAccountService.findById(id)
        return ResponseEntity(AccountResponse.of(account), HttpStatus.OK)
    }

    @PostMapping("/{id}/debit")
    fun debitAmount(
        @PathVariable id: String,
        @RequestBody request: OperationRequest
    ): ResponseEntity<OperationResponse> = executeBalanceOperation { operationService.debit(id, request.amount) }

    @PostMapping("/{id}/credit")
    fun creditAmount(
        @PathVariable id: String,
        @RequestBody request: OperationRequest
    ): ResponseEntity<OperationResponse> = executeBalanceOperation { operationService.credit(id, request.amount) }

    private fun executeBalanceOperation(
        operation: () -> Unit
    ): ResponseEntity<OperationResponse> {
        return try {
            operation()
            ResponseEntity(
                OperationResponse(OperationStatus.COMPLETED.response),
                HttpStatus.OK
            )
        } catch (be: BusinessException) {
            val response = OperationResponse(OperationStatus.REFUSED.response, be.msg)
            ResponseEntity(response, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/teste")
    fun kafka() {
        kafkaTemplate.send("TOPIC.OPERATION.EVENT", OperationEventMessage(
            "123",
            OperationType.DEBIT,
            BigDecimal.TEN
        ))
    }
}
