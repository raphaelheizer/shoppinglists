package br.heizer.shoppinglists.infrastructure.security.user

import br.heizer.shoppinglists.infrastructure.security.authentication.Role
import br.heizer.shoppinglists.infrastructure.validators.password.Password
import jakarta.validation.constraints.Email

interface UserCredentials {

    @get:Email
    val email: String

    @get:Password
    val password: String

    val roles: List<Role>

}