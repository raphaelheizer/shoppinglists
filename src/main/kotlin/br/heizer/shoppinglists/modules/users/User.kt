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

    avatarUrl: String

) : UserCredentials {
    val avatarUrl: String = avatarUrl.filterNot { it in listOf('<', '>') } // TODO: Criar @Annotation que faça um AnnotationProcessor gerar código pra criar um delegate que escreva um suplemento de código para fazer essa filtragem. Criar uma classe estática e "redirecionar" o setter/construtor para fazer isso
}