package com.spring.tutorial

import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpStatus
import java.util.*

internal class CustomerControllerTest {

    private val repository = mockk<CustomerRepository>()
    private val customerController = CustomerController(repository)

    @Test
    fun getCustomer() {

        // given:
        val id = UUID.randomUUID()
        val customer = Customer(id, "name")

        every { repository.findById(id) } returns customer // since the function findById will be called, we need to mockk it to prevent failure

        // when:
        val response = customerController.getCustomer(id.toString())


        // then:
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(customer)

    }

    @Test
    fun postCustomer() {

        //given:
        val customerPost = CustomerPost("name")

        every {
            repository.create(any())
        } just Runs


        //when:
        val response = customerController.postCustomer(customerPost)

        //then:
        verify { repository.create(any()) } // the functions which we call and they dnt give any specific thing back, but we want to test if they where called
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body?.name).isEqualTo("name")
    }
}