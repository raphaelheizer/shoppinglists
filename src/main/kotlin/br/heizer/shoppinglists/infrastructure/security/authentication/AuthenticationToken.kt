package br.heizer.shoppinglists.infrastructure.security.authentication

data class AuthenticationToken (
    val username: String,
    val authorities: String
)
