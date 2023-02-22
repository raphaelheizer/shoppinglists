package br.heizer.shoppinglists

import jakarta.validation.Valid
import jakarta.validation.Validation

class ValidationTestException(message: String) : Exception(message)

class TestUtils {
    companion object {
        fun <T> validateValidator(@Valid obj: T, propName: String) {
            val violations = Validation.buildDefaultValidatorFactory().validator.validateProperty(obj, propName)
                .toList()

            if (violations.isNotEmpty()) {
                val messages = violations.map { it.message }.reduce { acc, s -> (acc + "\n" + s) }

                throw ValidationTestException(messages)
            }
        }
    }
}