package com.example.services.account.resources.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepositoryJpa : JpaRepository<AccountEntity, String> {
    fun findByCpf(value: String): AccountEntity?
}
