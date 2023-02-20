package br.heizer.shoppinglists.infrastructure.validators.password

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordValidator : ConstraintValidator<Password, String> {

    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean =
        try {
            PasswordUtils.validate(password)
            true
        } catch (ex: IllegalArgumentException) {
            context!!
                .buildConstraintViolationWithTemplate(ex.message)
                .addConstraintViolation()
            false
        }
}
