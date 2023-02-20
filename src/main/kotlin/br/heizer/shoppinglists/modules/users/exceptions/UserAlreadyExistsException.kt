package br.heizer.shoppinglists.modules.users.exceptions

class UserAlreadyExistsException(override val message: String = "O usuário já existe") : Exception(message)
