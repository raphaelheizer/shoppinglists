package br.heizer.shoppinglists.infrastructure.security.tokens.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtPropertiesImpl(
    @Value("\${security.authorization.jwt.header}")
    override val header: String,

    @Value("\${security.authorization.jwt.secret}")
    override val secret: String,

    @Value("\${security.authorization.jwt.ignore-paths}")
    override val ignorePaths: Set<String>
) : JwtProperties
