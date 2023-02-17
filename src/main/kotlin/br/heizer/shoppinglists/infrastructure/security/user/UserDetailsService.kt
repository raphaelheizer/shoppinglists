package br.heizer.shoppinglists.infrastructure.security.user

import br.heizer.shoppinglists.infrastructure.security.user.exceptions.UserNotFoundException
import br.heizer.shoppinglists.modules.users.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User as CoreUserdetailsUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        (userRepository.findByEmail(username) ?: throw UserNotFoundException())
            .let {
                val userName: String = it.email
                val password: String = it.password
                val authorities = it.roles.map { role -> SimpleGrantedAuthority(role.name) }
                CoreUserdetailsUser(userName, password, authorities)
            }

}