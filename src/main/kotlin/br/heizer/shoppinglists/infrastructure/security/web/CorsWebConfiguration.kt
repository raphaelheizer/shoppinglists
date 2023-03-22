package br.heizer.shoppinglists.infrastructure.security.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration


@Component
class CorsWebConfiguration {
    @Value("\${security.web.cors.allow-credentials}")
    private val allowCredentials: Boolean? = null

    @Value("\${security.web.cors.allowed-origins}")
    private val allowedOrigins: List<String> = listOf()

    @Value("\${security.web.cors.allowed-methods}")
    private val allowedMethods: List<String> = listOf()

    @Value("\${security.web.cors.exposed-headers}")
    private val exposedHeaders: List<String> = listOf()

    @Value("\${security.web.cors.allowed-headers}")
    private val allowedHeaders: List<String> = listOf()

    @Value("\${security.web.cors.token-max-age}")
    private val tokenMaxAge: Long? = null

    @Bean(name = ["corsConfigurationSource"])
    fun corsConfigurationSource(): CorsConfiguration =
        CorsConfiguration()
            .also {
                it.allowCredentials = allowCredentials
                it.allowedOrigins = allowedOrigins
                it.allowedMethods = allowedMethods
                it.exposedHeaders = exposedHeaders
                it.allowedHeaders = allowedHeaders
                it.maxAge = tokenMaxAge
            }
}
