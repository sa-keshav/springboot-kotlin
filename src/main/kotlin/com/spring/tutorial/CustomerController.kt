package com.spring.tutorial

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

const val CUSTOMER_ENDPOINT = "/api/customers"

@RestController
@RequestMapping(CUSTOMER_ENDPOINT)
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping("{id}")
    fun getCustomer(@PathVariable id: Long): ResponseEntity<Customer> {

        val customer = customerRepository.findByIdOrNull(id)
        return ResponseEntity.ok().body(customer)
    }

    @PostMapping
    fun postCustomer(@RequestBody customerPost: CustomerPost): ResponseEntity<Customer> {

        val customer = Customer(name = customerPost.name, password = customerPost.password)
        val createdCustomer = customerRepository.save(customer)

        val id = createdCustomer.id
        return ResponseEntity.created(URI.create("$CUSTOMER_ENDPOINT/$id")).body(customer)
    }

    @PutMapping("{id}")
    fun putCustomer(@PathVariable id: Long, @RequestBody customer: Customer): ResponseEntity<Customer> {

        if (id != customer.id) {
            return ResponseEntity.badRequest().build()
        }
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        customerRepository.save(customer)
        return ResponseEntity.ok().body(customer)
    }

    @DeleteMapping("{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<Void> {

        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build()
        }
        customerRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getCustomers(): ResponseEntity<List<Customer>> {
        val customers = customerRepository.findAll()
        return ResponseEntity.ok().body(customers)
    }
}

data class CustomerPost(
        val name: String,
        val password: String
)