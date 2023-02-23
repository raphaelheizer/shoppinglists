package br.heizer.shoppinglists.infrastructure.security.validators.secureurl

import br.heizer.shoppinglists.TestUtils
import br.heizer.shoppinglists.ValidationTestException
import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrl
import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrlValidator
import jakarta.validation.Validator
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

private class TestClass(
    @field:SecureUrl
    private val secureUrl: String
)

@ExtendWith(SpringExtension::class)
@Import(LocalValidatorFactoryBean::class)
@SpringBootTest(
    classes = [TestUtils::class, SecureUrl::class, SecureUrlValidator::class],
    properties = ["security.web.cors.allowed-resource-origins = *"],
)
class SecureUrlTests {

    @Value("\${security.web.cors.allowed-resource-origins}")
    private val allowedTestResourceOrigins: Set<String> = setOf()

    @Autowired
    private lateinit var validator: Validator

    @BeforeEach
    fun initialize() {
        TestUtils.validator = validator
    }

    @Test
    fun `environment variables are loaded`() {
        assert(true) {
            allowedTestResourceOrigins.isNotEmpty()
        }

        println("loaded test environment variable $allowedTestResourceOrigins")
    }

    @Test
    fun `succeed when url is secure when any resource name is allowed`() {
        val urls = listOf(
            "https://domain1.com",
            "https://domain2.com",
            "https://domain3.com"
        )

        urls.forEach { url ->
            assertDoesNotThrow {
                TestUtils.validateValidator(TestClass(url), "secureUrl")
            }
        }
    }
}

@ExtendWith(SpringExtension::class)
@Import(LocalValidatorFactoryBean::class)
@SpringBootTest(
    classes = [TestUtils::class, SecureUrl::class, SecureUrlValidator::class],
    properties = ["security.web.cors.allowed-resource-origins = allowed-domain, allowed-domain-other"],
)
private class SecureUrlTestsFixedResourceName {

    @Autowired
    private lateinit var validator: Validator

    @BeforeEach
    fun initialize() {
        TestUtils.validator = validator
    }

    @Test
    fun `succeed when url is secure and resource name is allowed`() {
        val urls = listOf(
            "https://allowed-domain.com",
            "https://allowed-domain-other.com"
        )

        urls.forEach { url ->
            assertDoesNotThrow {
                TestUtils.validateValidator(TestClass(url), "secureUrl")
            }
        }
    }

    @Test
    fun `fail when url is secure but resource name is not allowed`() {
        assertThrows<ValidationTestException> {
            TestUtils.validateValidator(TestClass("https://not-allowed-domain.com"), "secureUrl")
        }
    }
}
