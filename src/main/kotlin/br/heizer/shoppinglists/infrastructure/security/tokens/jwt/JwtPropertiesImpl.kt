package br.heizer.shoppinglists.infrastructure.security.tokens.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtPropertiesImpl : JwtProperties {
    @Value("\${security.authorization.jwt.header}")
    override lateinit var header: String

    @Value("\${security.authorization.jwt.secret}")
    override lateinit var secret: String

    @Value("\${security.authorization.jwt.ignore-paths}")
    override lateinit var ignorePaths: Set<String>
}
