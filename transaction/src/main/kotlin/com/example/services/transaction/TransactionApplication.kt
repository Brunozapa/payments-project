package com.example.services.transaction

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class TransactionApplication

fun main(args: Array<String>) {
	runApplication<TransactionApplication>(*args)
}
