package br.heizer.shoppinglists.infrastructure.validators.nohtml

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NoHtmlValidator : ConstraintValidator<NoHtml, String> {

    companion object {
        const val validation = "^<.*/?>"
    }
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        doesNotContainHtml(value)

    private fun doesNotContainHtml(value: String?): Boolean =
        value?.matches(validation.toRegex()) ?: true

}