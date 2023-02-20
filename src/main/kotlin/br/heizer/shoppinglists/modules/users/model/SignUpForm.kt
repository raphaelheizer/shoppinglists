package br.heizer.shoppinglists.modules.users.model

import br.heizer.shoppinglists.infrastructure.validators.nonblank.NonBlank
import br.heizer.shoppinglists.infrastructure.validators.password.Password
import jakarta.validation.constraints.Email

data class SignUpForm(
    @Email
    @NonBlank
    val email: String,
    @NonBlank
    val name: String,
    @NonBlank
    val phone: Long,
    @Password
    @NonBlank
    val password: String
)