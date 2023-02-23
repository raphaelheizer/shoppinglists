package br.heizer.shoppinglists

import jakarta.validation.Valid
import jakarta.validation.Validation
import jakarta.validation.Validator
import kotlin.jvm.Throws

class ValidationTestException(message: String) : Exception(message)

class TestUtils {
    companion object {

        var validator: Validator = Validation.buildDefaultValidatorFactory().validator

        @Throws(ValidationTestException::class)
        fun <T> validateValidator(@Valid obj: T, propName: String) {
            val violations = validator.validateProperty(obj, propName)
                .toList()

            if (violations.isNotEmpty()) {
                val messages = violations.map { it.message }.reduce { acc, s -> (acc + "\n" + s) }

                throw ValidationTestException(messages)
            }
        }
    }
}