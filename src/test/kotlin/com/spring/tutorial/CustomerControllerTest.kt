package com.spring.tutorial

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import java.util.*

internal class CustomerControllerTest {

    private val repository = mockk<CustomerRepository>()
    private val customerController = CustomerController(repository)

    @Test
    fun getCustomer() {

        // given:
        val id = 1L
        val customer = Customer(id, "name", "password")
        every { repository.findByIdOrNull(id) } returns customer

        // when:
        val response = customerController.getCustomer(id)


        // then:
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(customer)

    }

    @Test
    fun postCustomer() {

        //given:
        val customerPost = CustomerPost("name", "password")
        every { repository.save(any<Customer>()) } returns Customer(1L, "name", "password")

        //when:
        val response = customerController.postCustomer(customerPost)

        //then:
        verify { repository.save(any<Customer>()) } // the functions which we call and they dnt give any specific thing back, but we want to test if they where called
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body?.name).isEqualTo("name")
    }

    @Test
    fun putCustomer() {

        //given:
        val id = 2L
        val customer = Customer(id, "name", "password")
        every { repository.existsById(id) } returns true
        every { repository.save(customer) } returns customer

        //when:
        val response = customerController.putCustomer(id, customer)

        //then:
        verify { repository.save(customer) }
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(customer)
    }

    @Test
    fun deleteCustomer() {

        //given:
        val id = 1L
        every { repository.deleteById(id) } just runs
        every { repository.existsById(id) } returns true

        //when:
        val response = customerController.deleteCustomer(id)

        //Then:
        verify { repository.deleteById(id) }
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun getCustomers() {

        //given:
        val customer1 = Customer(1L, "name1", "password")
        val customer2 = Customer(2L, "name2", "password")
        val customers = listOf(customer1, customer2)

        every { repository.findAll() } returns customers

        //when:
        val response = customerController.getCustomers()

        //Then:
        verify { repository.findAll() }
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(customers)
    }

}