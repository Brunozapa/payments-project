package com.example.services.account.domain.entity

import com.example.services.account.domain.exception.InvalidBirthdateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class BirthdateTest {
    @Test
    fun `should not accept birthdate for under 18 years`() {
        assertThrows<InvalidBirthdateException> {
            Birthdate(LocalDate.now().minusYears(15))
        }
    }
}