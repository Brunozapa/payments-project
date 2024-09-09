package com.example.services.account.application.service

import com.example.services.account.domain.exception.AccountNotFoundException
import com.example.services.account.domain.repository.AccountRepository
import com.example.services.account.factory.AccountFactory
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class OperationServiceTest {
    private val accountRepository = mockk<AccountRepository>()

    private val service = OperationService(accountRepository)

    @Test
    fun `should debit amount from account and save successfully`() {
        val amount = BigDecimal("100.00")
        val account = AccountFactory.create(amount)
        val accountDebited = AccountFactory.create()
        val accountId = account.id

        every { accountRepository.findById(accountId) } returns account
        every { accountRepository.advisoryLockById(accountId) } just Runs
        every { accountRepository.advisoryUnlockById(accountId) } just Runs
        every { accountRepository.save(any()) } returns accountDebited

        service.debit(accountId, amount)

        verify { accountRepository.advisoryLockById(accountId) }
        verify { accountRepository.findById(accountId) }
        verify { accountRepository.save(account) }
        verify { accountRepository.advisoryUnlockById(accountId) }
    }

    @Test
    fun `should credit amount from account and save successfully`() {
        val amount = BigDecimal("100.00")
        val account = AccountFactory.create()
        val creditedAccount = AccountFactory.create(BigDecimal("100.00"))
        val accountId = account.id

        every { accountRepository.findById(accountId) } returns account
        every { accountRepository.advisoryLockById(accountId) } just Runs
        every { accountRepository.advisoryUnlockById(accountId) } just Runs
        every { accountRepository.save(any()) } returns creditedAccount

        service.credit(accountId, amount)

        verify { accountRepository.advisoryLockById(accountId) }
        verify { accountRepository.findById(accountId) }
        verify { accountRepository.save(account) }
        verify { accountRepository.advisoryUnlockById(accountId) }
    }


    @Test
    fun `should throw exception when account not found during debit operation`() {
        val accountId = "1"
        val amount = BigDecimal("100.00")
        every { accountRepository.findById(accountId) } returns null
        every { accountRepository.advisoryLockById(accountId) } just Runs
        every { accountRepository.advisoryUnlockById(accountId) } just Runs

        assertThrows<AccountNotFoundException> { service.debit(accountId, amount) }
        verify { accountRepository.advisoryLockById(accountId) }
        verify { accountRepository.advisoryUnlockById(accountId) }
        verify(exactly = 0) { accountRepository.save(any()) }
    }
}