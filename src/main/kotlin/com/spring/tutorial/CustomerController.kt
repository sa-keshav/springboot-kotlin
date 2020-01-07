package com.spring.tutorial

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

const val CUSTOMER_ENDPOINT = "/api/customers"

@RestController
@RequestMapping(CUSTOMER_ENDPOINT)
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<Customer> {
        val customer = customerRepository.findById(UUID.fromString(id))
        return ResponseEntity.ok().body(customer)
    }

    @PostMapping
    fun postCustomer(@RequestBody customerPost: CustomerPost): ResponseEntity<Customer> {
        val id = UUID.randomUUID()
        val customer = Customer(id, customerPost.name)
        customerRepository.create(customer)

        return ResponseEntity.created(URI.create("$CUSTOMER_ENDPOINT/$id" )).body(customer)
    }


}

data class CustomerPost(
        val name: String
)