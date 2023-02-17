package br.heizer.shoppinglists.infrastructure.security.token.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtPropertiesImpl : JwtProperties {
    @Value("\${auth.jwt.header}")
    override lateinit var jwtHeader: String

    @Value("\${auth.jwt.secret}")
    override lateinit var jwtSecret: String

    @Value("jwtIgnorePaths")
    override lateinit var jwtIgnorePaths: Set<String>
}
