package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidBalanceException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class BalanceTest {

    @Test
    fun `should add amount to balance successfully`() {
        val balance = Balance(BigDecimal.ONE).add(BigDecimal.ONE)
        assertEquals(BigDecimal(2), balance.value)
    }

    @Test
    fun `should subtract amount of balance successfully`() {
        val balance = Balance(BigDecimal(2)).subtract(BigDecimal.ONE)
        assertEquals(BigDecimal.ONE, balance.value)
    }

    @Test
    fun `should not subtract amount of balance if result less then zero`() {
        assertThrows<InvalidBalanceException> { Balance(BigDecimal.ZERO).subtract(BigDecimal.ONE) }
    }
}
