package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value

class SecureUrlValidator : ConstraintValidator<SecureUrl, String> {

    @Value("\${security.web.cors.allowed-resource-origins}")
    var allowedResourceOrigins = setOf<String>()

    companion object { // [(https|wss):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)
        val secureUrlRegex =
            Regex("""[(https|wss):\/\/(www\.)?a-zA-Z0-9@:%._\-\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\-\+.~#?&//=]*)""")
    }

    override fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean =
        if (url == null) {
            false
        } else isAllowedOrigin(url) && url.matches(secureUrlRegex)

    private fun isAllowedOrigin(url: String): Boolean =
        allowedResourceOrigins.contains("*") || allowedResourceOrigins.any { allowedUrl -> url.contains(allowedUrl) }
}
