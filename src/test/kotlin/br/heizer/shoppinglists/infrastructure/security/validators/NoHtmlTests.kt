package br.heizer.shoppinglists.infrastructure.security.validators

import br.heizer.shoppinglists.infrastructure.validators.nohtml.NoHtml
import jakarta.validation.Valid
import jakarta.validation.Validation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class NoHtmlTests {

    private class TestClass(
        @NoHtml
        val prop: String
    )

    @Test
    fun `validation should succeed`() {
        assertDoesNotThrow {
            validate(TestClass(prop = "There is no script tag"))
        }
    }

    @Test
    fun `validation should fail for @NoHtml`() {
        val exception = assertThrows<Exception> {
            validate(TestClass(prop = "<script>function anything { doStuff(); }</script>"))
        }

        assert(true) {
            exception.message!!.contains("Html tags or scripts are not allowed")
        }
    }

    private fun validate(@Valid obj: TestClass) {
        val violations = Validation.buildDefaultValidatorFactory().validator.validateProperty(obj, "prop")
            .toList()

        if (violations.isNotEmpty()) {
            val messages = violations.map { it.message }.reduce { acc, s -> (acc + "\n" + s) }

            throw Exception(messages)
        }
    }
}