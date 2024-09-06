package com.example.services.account

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAutoConfiguration
class AccountApplication

fun main(args: Array<String>) {
	runApplication<AccountApplication>(*args)
}
