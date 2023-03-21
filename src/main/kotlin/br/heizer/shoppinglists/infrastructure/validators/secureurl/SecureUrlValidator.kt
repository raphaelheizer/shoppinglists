package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SecureUrlValidator(private val secureUrlUtils: SecureUrlUtils) : ConstraintValidator<SecureUrl, String> {

    override fun isValid(url: String?, context: ConstraintValidatorContext?): Boolean =
        secureUrlUtils.isValid(url, context)

}
