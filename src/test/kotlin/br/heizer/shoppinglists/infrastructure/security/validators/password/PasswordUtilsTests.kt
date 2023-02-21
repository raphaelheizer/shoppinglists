package br.heizer.shoppinglists.infrastructure.security.validators.password

import br.heizer.shoppinglists.infrastructure.validators.password.PasswordUtils.Companion.minimumLength
import br.heizer.shoppinglists.infrastructure.validators.password.PasswordUtils.Companion.validate
import br.heizer.shoppinglists.infrastructure.validators.password.PasswordUtils.Companion.validateLength
import br.heizer.shoppinglists.infrastructure.validators.password.PasswordUtils.Companion.validatePasswordStrength
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PasswordUtilsTests {

    private var validPasswords = listOf(
        "Password#@!99",
        "password.Valid@1234",
        "v4l1dP4ssword!",
        "123456_78ABc@"
    )
    private var shortPassword: String = "1234567"

    private val weakPasswords = listOf(
        "abcdefgh",
        "Password123",
        "Password#@!",
        "password#@!"
    )

    @Test
    fun `password is valid`() {
        assertDoesNotThrow {
            validPasswords.forEach {
                validateLength(it)
                validatePasswordStrength(it)
                validate(it)
            }
        }
    }

    @Test
    fun `password must not be null`() {
        val exception = assertThrows<IllegalArgumentException> {
            validate(null)
        }

        assert(true) {
            exception.message == "Password must not be null"
        }
    }

    @Test
    fun `password is too short`() {
        val exception = assertThrows<IllegalArgumentException> {
            validateLength(shortPassword)
            validate(shortPassword)
        }

        assert(true) {
            shortPassword.length < minimumLength
        }

        assert(true) {
            exception.message == "Password length must be equal or greater than $minimumLength characters"
        }
    }

    @Test
    fun `password is too weak`() {
        weakPasswords.forEach {
            var exception = assertThrows<IllegalArgumentException> {
                validatePasswordStrength(it)
            }

            assert(true) {
                exception.message == "Password is too weak"
            }

            exception = assertThrows {
                validate(it)
            }

            assert(true) {
                exception.message == "Password is too weak"
            }
        }
    }
}