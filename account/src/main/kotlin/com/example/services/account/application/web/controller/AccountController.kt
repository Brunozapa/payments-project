package com.example.services.account.application.web.controller

import com.example.services.account.application.service.CreateAccountService
import com.example.services.account.application.service.FindAccountService
import com.example.services.account.application.service.OperationService
import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.application.web.request.OperationRequest
import com.example.services.account.application.web.response.AccountResponse
import com.example.services.account.application.web.response.OperationResponse
import com.example.services.account.application.web.response.OperationStatus
import com.example.services.account.domain.exception.BusinessException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val createAccountService: CreateAccountService,
    private val operationService: OperationService,
    private val findAccountService: FindAccountService,
) {
    @PostMapping
    fun create(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResponse> {
        logger.info { "Create account request received. $request" }
        val account = createAccountService.execute(request)
        return ResponseEntity(AccountResponse.of(account), HttpStatus.CREATED).also {
            logger.info { "Create account request processed with success." }
        }
    }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): ResponseEntity<AccountResponse> {
        logger.info { "Find account request received for id: $id" }
        val account = findAccountService.findById(id)
        return ResponseEntity(AccountResponse.of(account), HttpStatus.OK).also {
            logger.info { "Find account request processed with success for id: $id" }
        }
    }

    @PostMapping("/{id}/debit")
    fun debitAmount(
        @PathVariable id: String,
        @RequestBody request: OperationRequest
    ): ResponseEntity<OperationResponse> {
        logger.info { "Debit request received for account id: $id with amount: ${request.amount}" }
        return executeBalanceOperation { operationService.debit(id, request.amount) }.also {
            logger.info { "Debit request processed with success for account id: $id" }
        }
    }

    @PostMapping("/{id}/credit")
    fun creditAmount(
        @PathVariable id: String,
        @RequestBody request: OperationRequest
    ): ResponseEntity<OperationResponse> {
        logger.info { "Credit request received for account id: $id with amount: ${request.amount}" }
        return executeBalanceOperation { operationService.credit(id, request.amount) }.also {
            logger.info { "Credit request processed with success for account id: $id" }

        }
    }

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

    companion object : KLogging()
}
