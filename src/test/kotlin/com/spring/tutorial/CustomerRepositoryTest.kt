package com.spring.tutorial

import io.mockk.every
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.apache.tomcat.util.bcel.classfile.JavaClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.jdbc.core.RowMapper
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


internal class CustomerRepositoryTest {

    val jdbcTemplate = mockk<JdbcTemplate>()
    val customerRepository = CustomerRepository(jdbcTemplate)

    @Test
    fun findById() {

        //given:
        val id = UUID.randomUUID()
        val sql = "Select * from customers where id = ?"// TODO: Edit sql add ? instead of id... check loading service for examples (ff loading service)
        //val sql =  "Select * from customers where id= ?"
        val customer = Customer(id, "name")


        every { jdbcTemplate.query(sql, any<Array<Any>>(), any<RowMapper<Customer>>()) } returns listOf(customer)

        //when:
        val result = customerRepository.findById(id)


        //then:
        assertThat(result?.name).isEqualTo("name")
        assertThat(result?.id).isEqualTo(id)

    }


    @Test
    fun create() {

        //given:
        val customer = Customer(UUID.randomUUID(), "name")
        val sql = "Insert into customers(id, name)values(?, ?)"

        every { jdbcTemplate.update(sql, customer.id, customer.name) } returns 1

        //when:
        customerRepository.create(customer)

        //then:
        verify { jdbcTemplate.update(sql, customer.id, customer.name) }

    }
}


