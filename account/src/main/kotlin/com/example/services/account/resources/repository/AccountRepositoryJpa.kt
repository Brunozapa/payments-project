package com.example.services.account.resources.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AccountRepositoryJpa : JpaRepository<AccountEntity, String> {
    fun findByCpf(value: String): AccountEntity?

    @Query(value = "select pg_try_advisory_lock(:lockKey)", nativeQuery = true)
    fun tryAdvisoryLock(@Param("lockKey") key: Long): Boolean

    @Query(value = "select pg_advisory_unlock(:lockKey)", nativeQuery = true)
    fun advisoryUnlock(@Param("lockKey") key: Long): Boolean
}
