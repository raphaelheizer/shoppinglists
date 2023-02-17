package br.heizer.shoppinglists.infrastructure.security.user

import br.heizer.shoppinglists.infrastructure.security.authentication.Role
import jakarta.validation.constraints.Email

interface UserCredentials {

    @get:Email
    val email: String

    val password: String

    val roles: List<Role>

}