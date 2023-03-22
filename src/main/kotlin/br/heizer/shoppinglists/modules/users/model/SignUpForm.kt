package br.heizer.shoppinglists.modules.users.model

import br.heizer.shoppinglists.infrastructure.validators.nohtml.NoHtml
import br.heizer.shoppinglists.infrastructure.validators.nonblank.NonBlank
import br.heizer.shoppinglists.infrastructure.validators.password.Password
import jakarta.validation.constraints.Email

data class SignUpForm(
    @Email
    @NoHtml
    @NonBlank
    val email: String,
    @NoHtml
    @NonBlank
    val name: String,
    @NonBlank
    val phone: Long,
    @NoHtml
    @Password
    @NonBlank
    val password: String
)