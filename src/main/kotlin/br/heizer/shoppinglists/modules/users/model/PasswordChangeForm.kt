package br.heizer.shoppinglists.modules.users.model

import br.heizer.shoppinglists.infrastructure.validators.nohtml.NoHtml
import br.heizer.shoppinglists.infrastructure.validators.nonblank.NonBlank

data class PasswordChangeForm(
    @NoHtml @NonBlank
    val currentPassword: String,
    @NoHtml @NonBlank
    val newPassword: String
)