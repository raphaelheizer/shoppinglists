package br.heizer.shoppinglists.infrastructure.validators.password

import kotlin.jvm.Throws

class PasswordUtils {

    companion object {
        private const val passwordStrengthValidation =
            "^(?!\\[]<>;/\\\\)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+])[A-Za-z\\d!@#\$%^&*_+.]*\$"
        const val minimumLength = 8

        @Throws(IllegalArgumentException::class)
        fun validateLength(password: String) {
            if (password.length < minimumLength)
                throw IllegalArgumentException("Password length must be equal or greater than $minimumLength characters")
        }

        @Throws(IllegalArgumentException::class)
        fun validatePasswordStrength(password: String) {
            if (!password.matches(passwordStrengthValidation.toRegex()))
                throw IllegalArgumentException("Password is too weak")
        }

        @Throws(IllegalArgumentException::class)
        fun validate(password: String?) {
            password.also {
                it ?: throw IllegalArgumentException("Password must not be null")
                validateLength(it)
                validatePasswordStrength(it)
            }
        }
    }
}
