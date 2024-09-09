package com.example.services.account.application.web.controller

import com.example.services.account.application.service.CreateAccountService
import com.example.services.account.application.service.FindAccountService
import com.example.services.account.application.service.OperationService
import com.example.services.account.application.web.request.OperationRequest
import com.example.services.account.application.web.response.OperationStatus
import com.example.services.account.domain.exception.BusinessException
import com.example.services.account.factory.AccountFactory
import com.example.services.account.factory.CreateAccountRequestFactory
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus
import java.math.BigDecimal

class AccountControllerTest {

    private val createAccountService = mockk<CreateAccountService>()
    private val operationService = mockk<OperationService>()
    private val findAccountService = mockk<FindAccountService>()

    private val controller = AccountController(createAccountService, operationService, findAccountService)

    @Test
    fun `should create account and return created response`() {
        val request = CreateAccountRequestFactory.create()
        every { createAccountService.execute(request) } returns AccountFactory.create(request)

        val response = controller.create(request)

        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    fun `should find account by id and return ok response`() {
        val accountId = "1"
        val account = AccountFactory.create()

        every { findAccountService.findById(accountId) } returns account

        val response = controller.find(accountId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(account.id, response.body?.id)
    }

    @Test
    fun `should debit amount from account and return ok response`() {
        val accountId = "1"
        val amount = BigDecimal("100.00")
        val request = OperationRequest(amount = amount)
        every { operationService.debit(accountId, amount) } just runs

        val response = controller.debitAmount(accountId, request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(OperationStatus.COMPLETED.response, response.body?.status)
    }

    @Test
    fun `should credit amount from account and return correct operation status`() {
        val accountId = "1"
        val amount = BigDecimal("100.00")
        val request = OperationRequest(amount = amount)
        every { operationService.credit(accountId, amount) } just runs

        val response = controller.creditAmount(accountId, request)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(OperationStatus.COMPLETED.response, response.body?.status)
    }

    @Test
    fun `should handle error when crediting amount and return correct operation status`() {
        val accountId = "1"
        val amount = BigDecimal("100.00")
        val request = OperationRequest(amount = amount)
        every { operationService.credit(accountId, amount) } throws BusinessException("", "")

        val response = controller.creditAmount(accountId, request)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals(OperationStatus.REFUSED.response, response.body?.status)
    }
}
