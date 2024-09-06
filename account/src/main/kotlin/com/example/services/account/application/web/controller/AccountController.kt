package com.example.services.account.application.web.controller

import com.example.services.account.application.service.CreateAccountService
import com.example.services.account.application.web.request.CreateAccountRequest
import com.example.services.account.application.web.response.AccountResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountController(
    private val createAccountService: CreateAccountService
) {
    @PostMapping
    fun create(@RequestBody request: CreateAccountRequest): ResponseEntity<AccountResponse> {
        val account = createAccountService.execute(request)
        return ResponseEntity(AccountResponse.of(account), HttpStatus.CREATED)
    }
}
