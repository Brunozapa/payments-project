package com.example.services.account.application.service

import com.example.services.account.domain.exception.AccountNotFoundException
import com.example.services.account.domain.repository.AccountRepository
import com.example.services.account.factory.AccountFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindAccountServiceTest {
    private val accountRepository = mockk<AccountRepository>()

    private val service = FindAccountService(accountRepository)

    @Test
    fun `should find account by id and return account`() {
        val account = AccountFactory.create()
        val accountId = account.id

        every { accountRepository.findById(accountId) } returns account

        val result = service.findById(accountId)

        assertEquals(account, result)
        verify { accountRepository.findById(accountId) }
    }

    @Test
    fun `should throw exception when account not found by id`() {
        val accountId = "accountId"

        every { accountRepository.findById(accountId) } returns null

        assertThrows<AccountNotFoundException> { service.findById(accountId) }
        verify { accountRepository.findById(accountId) }
    }
}
