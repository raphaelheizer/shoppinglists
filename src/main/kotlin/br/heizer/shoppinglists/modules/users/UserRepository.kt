package br.heizer.shoppinglists.modules.users

import br.heizer.shoppinglists.infrastructure.security.user.UserCredentialsRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>, UserCredentialsRepository<User>