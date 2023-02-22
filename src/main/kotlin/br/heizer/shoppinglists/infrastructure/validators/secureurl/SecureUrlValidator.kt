package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SecureUrlValidator : ConstraintValidator<SecureUrl, String> {

    @Value("\${security.web.cors.allowed-resource-origins}")
    var allowedResourceOrigins = setOf<String>()

    companion object {
        val secureUrlRegex =
            Regex("^(https://|wss://)[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+([a-zA-Z0-9_\\-.,@?^=%&:/~+#]*[a-zA-Z0-9_\\-@?^=%&/~+#])?\$")
    }

    override fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean {
        if (url == null) {
            return false
        }
        return isAllowedOrigin(url) && url.matches(secureUrlRegex)
    }

    private fun isAllowedOrigin(url: String): Boolean =
        allowedResourceOrigins.contains("*") || allowedResourceOrigins.any { allowedUrl -> url.contains(allowedUrl) }
}
