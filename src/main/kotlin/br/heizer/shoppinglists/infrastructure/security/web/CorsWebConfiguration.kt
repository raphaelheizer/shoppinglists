package br.heizer.shoppinglists.infrastructure.security.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration


@Component
class CorsWebConfiguration {

    @Value("\${application.front-end-origin}")
    private val frontEndOrigin: String = ""

    @Bean(name = ["corsConfigurationSource"])
    fun corsConfigurationSource(): CorsConfiguration =
        CorsConfiguration()
            .also {
                it.allowCredentials = true
                it.allowedOrigins = listOf(frontEndOrigin)
                it.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                it.exposedHeaders = listOf("Authorization")
                it.allowedHeaders = listOf("*")
                it.maxAge = 3600L
            }
}
