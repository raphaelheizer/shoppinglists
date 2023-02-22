package br.heizer.shoppinglists.infrastructure.security.validators.nonblank

import br.heizer.shoppinglists.TestUtils
import br.heizer.shoppinglists.ValidationTestException
import br.heizer.shoppinglists.infrastructure.validators.nonblank.NonBlank
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class NonBlankTests {

    private class TestClass(
        @NonBlank
        private val prop: String?
    )

    @Test
    fun `succeed when not blank`() {
        assertDoesNotThrow {
            TestUtils.validateValidator(TestClass("Non Blank Field"), "prop")
        }
    }

    @Test
    fun `fail when blank`() {
        val exception = assertThrows<ValidationTestException> {
            TestUtils.validateValidator(TestClass(""), "prop")
        }

        assert(true) {
            exception.message == "Field must not be blank"
        }
    }

    @Test
    fun `fail when null`() {
        val exception = assertThrows<ValidationTestException> {
            TestUtils.validateValidator(TestClass(null), "prop")
        }

        assert(true) {
            exception.message == "Field must not be blank"
        }
    }

}