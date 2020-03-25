package com.spring.tutorial


import javax.persistence.*

@Entity
@Table(name= "customers")
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null ,
        val name: String,
        val password: String

)