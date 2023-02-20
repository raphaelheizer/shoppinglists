package br.heizer.shoppinglists.modules.users.model

data class PasswordChangeForm(
    val currentPassword: String,
    val newPassword: String
)