package com.spring.tutorial

import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomerRepository(private val jdbcTemplate: JdbcTemplate) {

    fun findById(id: UUID): Customer? {
        val sql = "Select * from customers where id = ?"// it is possible of sql injection!!! change the direct variable to ? and then add the variable

        var customer = jdbcTemplate.query(sql, arrayOf(id)) { it, _ ->
            Customer(
                    id = UUID.fromString(it.getString("id")),
                    name = it.getString("name")
            )

        }
        return DataAccessUtils.singleResult(customer)

    }

    fun create(customer: Customer) {
        val sql = "Insert into customers(id, name)values(?, ?)"
        jdbcTemplate.update(sql, customer.id, customer.name)
    }
}