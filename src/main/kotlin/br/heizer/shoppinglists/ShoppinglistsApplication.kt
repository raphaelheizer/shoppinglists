package br.heizer.shoppinglists

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShoppinglistsApplication

fun main(args: Array<String>) {
	runApplication<ShoppinglistsApplication>(*args)
}
