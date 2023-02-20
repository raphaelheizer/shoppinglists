package br.heizer.shoppinglists.infrastructure.validators.password

import kotlin.jvm.Throws

class PasswordUtils {

    companion object {
        private const val passwordStrengthValidation =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=\\[]\\{};':\"\\\\|,.<>/?])"

        @Throws(IllegalArgumentException::class)
        fun validateLength(password: String) {
            val minimumLength = 8
            if (password.length < minimumLength)
                throw IllegalArgumentException("Password length must be equal or greater than $minimumLength characters")
        }

        @Throws(IllegalArgumentException::class)
        fun validatePasswordNotTooWeak(password: String) {
            if (!password.matches(passwordStrengthValidation.toRegex()))
                throw IllegalArgumentException("Password is too weak")
        }

        @Throws(IllegalArgumentException::class)
        fun validate(password: String?) =
            password.also {
                if (it == null) throw IllegalArgumentException("Password must not be null")
                validateLength(it)
                validatePasswordNotTooWeak(it)
            }
    }
}
