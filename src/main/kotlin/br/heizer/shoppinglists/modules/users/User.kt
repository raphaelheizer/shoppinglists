package br.heizer.shoppinglists.modules.users

import br.heizer.shoppinglists.infrastructure.security.authentication.Role
import br.heizer.shoppinglists.infrastructure.security.user.UserCredentials
import org.springframework.data.mongodb.core.mapping.MongoId

class User(
    @MongoId
    val id: String,

    override val email: String,

    override val password: String,

    override val roles: List<Role>

) : UserCredentials