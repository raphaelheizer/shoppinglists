package br.heizer.shoppinglists.infrastructure.security.validators.nohtml

import br.heizer.shoppinglists.TestUtils
import br.heizer.shoppinglists.ValidationTestException
import br.heizer.shoppinglists.infrastructure.validators.nohtml.NoHtml
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@Suppress("unused")
class NoHtmlTests {

    private class TestClass(
        @field:NoHtml
        val prop: String
    )

    @NoHtml
    private class TestClassAnnotated(
        val prop: String
    )

    @Test
    fun `validation should succeed`() {
        assertDoesNotThrow {
            TestUtils.validateValidator(TestClass(prop = "There is no script tag"), "prop")
        }

        assertDoesNotThrow {
            TestUtils.validateValidator(TestClassAnnotated(prop = "There is no script tag"), "prop")
        }
    }

    @Test
    fun `validation should fail for @NoHtml`() {
        val exception = assertThrows<ValidationTestException> {
            TestUtils.validateValidator(TestClass(prop = "<script>function anything { doStuff(); }</script>"), "prop")
        }

        assertThrows<ValidationTestException> {
            TestUtils.validateValidator(TestClassAnnotated(prop = "<script>function anything { doStuff(); }</script>"), "prop")
        }

        assert(true) {
            exception.message!!.contains("Html tags or scripts are not allowed")
        }
    }

}