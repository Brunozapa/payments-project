package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidTransactionAmountException
import com.example.services.account.factory.AccountFactory
import com.example.services.account.factory.AccountToCreateFactory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class AccountTest {

    @Test
    fun `should create a valid account`() {
        val account = Account.create(AccountToCreateFactory.create())
        assertNotNull(account)
        assertEquals(BigDecimal.ZERO, account.balance.value)
    }

    @Test
    fun `should create a valid account without nullable attributes`() {
        val account = Account.create(AccountToCreateFactory.createWithoutNullables())
        assertNotNull(account)
        assertEquals(BigDecimal.ZERO, account.balance.value)
        assertNull(account.phone)
        assertNull(account.email)
    }

    @Test
    fun `should credit balance with success`() {
        val account = AccountFactory.create(BigDecimal.TEN).credit(BigDecimal.TEN)
        assertEquals(BigDecimal(20), account.balance.value)
    }

    @Test
    fun `should debit balance with success`() {
        val account = AccountFactory.create(BigDecimal.TEN).debit(BigDecimal.TEN)
        assertEquals(BigDecimal.ZERO, account.balance.value)
    }

    @Test
    fun `should not credit balance if amount is zero`() {
        assertThrows<InvalidTransactionAmountException> {
            AccountFactory.create(BigDecimal.TEN).credit(BigDecimal.ZERO)
        }
    }

    @Test
    fun `should not debit balance if amount is zero`() {
        assertThrows<InvalidTransactionAmountException> {
            AccountFactory.create(BigDecimal.TEN).debit(BigDecimal.ZERO)
        }
    }
}