package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value

class SecureUrlImpl : ConstraintValidator<SecureUrl, String> {

    @Value("\${security.web.cors.allowed-resource-origins}")
    private lateinit var allowedResourceOrigins: Set<String>

    override fun initialize(constraintAnnotation: SecureUrl) {
        // Define initialization behavior
    }

    override fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean {
        if (url == null) {
            return false
        }
        val secureUrlRegex = Regex("^(https://)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+([a-zA-Z0-9_\\-.,@?^=%&:/~+#]*[a-zA-Z0-9_\\-@?^=%&/~+#])?\$")
        return isAllowedOrigin(url) && url.matches(secureUrlRegex)
    }

    private fun isAllowedOrigin(url: String): Boolean {
        return allowedResourceOrigins.any {allowedUrl ->
            url.contains(allowedUrl)
        }
    }
}
