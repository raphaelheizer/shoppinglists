package br.heizer.shoppinglists.infrastructure.security.token.jwt

interface JwtProperties {
    val header: String
    val secret: String
    val ignorePaths: Set<String>
}