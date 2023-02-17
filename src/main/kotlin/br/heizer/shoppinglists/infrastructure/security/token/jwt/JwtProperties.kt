package br.heizer.shoppinglists.infrastructure.security.token.jwt

interface JwtProperties {
    val jwtHeader: String
    val jwtSecret: String
    val jwtIgnorePaths: Set<String>
}