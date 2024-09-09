package com.example.services.account.application.service

import com.example.services.account.domain.exception.AccountCreateException
import com.example.services.account.domain.repository.AccountRepository
import com.example.services.account.factory.AccountFactory
import com.example.services.account.factory.CreateAccountRequestFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateAccountServiceTest {
    private val accountRepository = mockk<AccountRepository>()

    private val service = CreateAccountService(accountRepository)

    @Test
    fun `should create account successfully`() {
        val request = CreateAccountRequestFactory.create()
        val account = AccountFactory.create(request)

        every { accountRepository.findByCpf(request.cpf) } returns null
        every { accountRepository.save(any()) } returns account

        val result = service.execute(request)

        assertEquals(account, result)
        verify { accountRepository.findByCpf(request.cpf) }
        verify { accountRepository.save(any()) }
    }

    @Test
    fun `should throw exception when account with CPF already exists`() {
        val request = CreateAccountRequestFactory.create()
        val account = AccountFactory.create(request)

        every { accountRepository.findByCpf(request.cpf) } returns account

        assertThrows<AccountCreateException> { service.execute(request) }
        verify { accountRepository.findByCpf(request.cpf) }
        verify(exactly = 0) { accountRepository.save(any()) }
    }
}