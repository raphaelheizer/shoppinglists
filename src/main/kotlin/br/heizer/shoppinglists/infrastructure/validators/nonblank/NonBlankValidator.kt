package br.heizer.shoppinglists.infrastructure.validators.nonblank

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NonBlankValidator : ConstraintValidator<NonBlank, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        !value.isNullOrBlank()
}