package br.heizer.shoppinglists.infrastructure.security.user.exceptions

class UserNotFoundException(override val message: String? = "User not found") : Exception(message = message)