package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SecureUrlUtils {

    @Value("\${security.web.cors.allowed-resource-origins}")
    private val allowedResourceOrigins = setOf<String>()

    @Value("\${security.web.cors.allowed-protocols}")
    private val allowedProtocols = setOf<String>()

    companion object {
        const val urlPrefixPattern = """://(?:\w*\.)?"""
        const val urlDomainPattern = """(?<subdomain>([\w-]*)\.(?=($'domain')))?(?<domain>[\w-]*)\."""
        const val pathsPattern = """(?:\w{2,4}\.?){1,2}(?:\w{2})?(?:/\w+)*(?:#\w+)?"""
        const val argumentsPattern = """(?:\?\w+=\w+(?:&?\w+=\w+)*)?(:\d{1,5})?/?"""
    }

    fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean =
        if (url == null) {
            false
        } else {
            val protocols = if (allowedProtocols.isNotEmpty()) {
                allowedProtocols.joinToString("|")
            } else "https"

            url.matches(
                Regex("($protocols)$urlPrefixPattern$urlDomainPattern$pathsPattern$argumentsPattern"))
                    && isAllowedOrigin(url)
        }

    private fun isAllowedOrigin(url: String): Boolean =
        allowedResourceOrigins.contains("*") || allowedResourceOrigins.any { allowedUrl -> isInDomain(url, allowedUrl) }

    private fun isInDomain(url: String, allowedUrl: String): Boolean {
        val (urlSubdomain, urlDomain) = extractUrlDomainName(url)

        if (!allowedUrl.contains(".")) {
            return urlDomain == allowedUrl
        }

        val (allowedUrlSubdomain, allowedUrlDomain) = extractUrlDomainName(allowedUrl)

        return if (urlSubdomain != null) {
            urlSubdomain == allowedUrlSubdomain && urlDomain == allowedUrlDomain
        } else {
            urlDomain == allowedUrlDomain
        }
    }

    private fun extractUrlDomainName(url: String): Pair<String?, String?> {
        val ignorePrefixes = """(?<=\w*\.)?"""
        val urlRegexIgnorePeriods = urlDomainPattern.replace("\\.", "(?=\\.)")
        val rgx = Regex(ignorePrefixes + urlRegexIgnorePeriods)
        val result = rgx.find(url)

        val subdomain = (result?.groups as MatchNamedGroupCollection?)?.get("subdomain")?.value
        val domain = (result?.groups as MatchNamedGroupCollection?)?.get("domain")?.value

        return Pair(subdomain, domain)
    }
}