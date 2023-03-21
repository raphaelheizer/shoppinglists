package br.heizer.shoppinglists.modules.users

import br.heizer.shoppinglists.infrastructure.security.authentication.Role
import br.heizer.shoppinglists.infrastructure.security.user.UserCredentials
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "user")
class User(
    @MongoId
    val id: String? = null,

    override val email: String,

    @JsonIgnore
    override var password: String,

    override val roles: List<Role>,

    val avatarUrl: String

) : UserCredentials