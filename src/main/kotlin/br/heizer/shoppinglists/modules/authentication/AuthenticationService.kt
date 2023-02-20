package br.heizer.shoppinglists.modules.authentication

import br.heizer.shoppinglists.modules.users.model.PasswordChangeForm
import br.heizer.shoppinglists.modules.users.model.SignUpForm
import java.security.Principal

interface AuthenticationService {
    fun signIn(email: String, credentials: String): String
    fun signUp(signUpForm: SignUpForm)
    fun changePassword(passwordChange: PasswordChangeForm, principal: Principal)
}