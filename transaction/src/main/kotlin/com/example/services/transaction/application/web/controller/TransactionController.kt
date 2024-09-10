package com.example.services.transaction.application.web.controller

import com.example.services.transaction.application.service.FindTransactionService
import com.example.services.transaction.application.service.StartTransactionService
import com.example.services.transaction.application.web.request.PaymentRequest
import com.example.services.transaction.application.web.request.ReceiptRequest
import com.example.services.transaction.application.web.response.TransactionResponse
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
@RequestMapping("/transaction")
class TransactionController(
    private val startTransactionService: StartTransactionService,
    private val findTransactionService: FindTransactionService
) {

    @PostMapping("/payment")
    fun startPayment(
        @RequestBody request: PaymentRequest
    ): ResponseEntity<TransactionResponse> {
        logger.info { "Payment transaction request received. $request" }
        val transaction = startTransactionService.startPayment(request)
        return ResponseEntity(TransactionResponse.of(transaction), HttpStatus.CREATED)
    }

    @PostMapping("/receipt")
    fun startReceipt(
        @RequestBody request: ReceiptRequest
    ): ResponseEntity<TransactionResponse> {
        logger.info { "Receipt transaction request received. $request" }
        val transaction = startTransactionService.startReceipt(request)
        return ResponseEntity(TransactionResponse.of(transaction), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable id: String): ResponseEntity<TransactionResponse> {
        logger.info { "Get transaction request received for transactionId $id" }
        val transaction = findTransactionService.findById(id)
        return ResponseEntity(TransactionResponse.of(transaction), HttpStatus.OK)
    }

    companion object : KLogging()
}