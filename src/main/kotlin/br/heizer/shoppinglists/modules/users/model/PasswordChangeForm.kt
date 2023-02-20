package br.heizer.shoppinglists.modules.users.model

import br.heizer.shoppinglists.infrastructure.validators.nohtml.NoHtml

@NoHtml
data class PasswordChangeForm(
    val currentPassword: String,
    val newPassword: String
)