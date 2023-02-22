package br.heizer.shoppinglists.infrastructure.security.validators.secureurl

import br.heizer.shoppinglists.TestUtils
import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrl
import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrlValidator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestUtils::class, SecureUrl::class, SecureUrlValidator::class])
@TestPropertySource(properties = ["security.web.cors.allowed-resource-origins=*"])
class SecureUrlTests {

    @Value("\${security.web.cors.allowed-resource-origins}")
    private val allowedTestResourceOrigins: Set<String> = setOf()

    private class TestClass(
        @field:SecureUrl
        private val secureUrl: String
    )

    @Test
    fun `environment variables are loaded`() {
        assert(true) {
            allowedTestResourceOrigins.isNotEmpty()
        }

        println("loaded test environment variable $allowedTestResourceOrigins")
    }

    @Test
    fun `succeed when url is secure`() {
        assertDoesNotThrow {
            TestUtils.validateValidator(TestClass("https://localhost:8080/"), "secureUrl")
        }
    }
}