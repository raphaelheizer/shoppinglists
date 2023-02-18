package br.heizer.shoppinglists.infrastructure.security.token.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtPropertiesImpl : JwtProperties {
    @Value("\${security.authorization.jwt.header}")
    override lateinit var jwtHeader: String

    @Value("\${security.authorization.jwt.secret}")
    override lateinit var jwtSecret: String

    @Value("\${security.authorization.jwt.ignore-paths}")
    override lateinit var jwtIgnorePaths: Set<String>
}
