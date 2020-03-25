package com.spring.tutorial

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType
import javax.persistence.QueryHint

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "javax.persistence.lock.timeout", value = "1000"))
    fun findById(): Customer?
}