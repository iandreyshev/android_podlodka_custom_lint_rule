package ru.iandreyshev.lib_detekt_rules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.*

class RedundantWhenExpression(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue
        get() = Issue(
            id = ID,
            description = DEFAULT_DESCRIPTION,
            severity = Severity.Warning,
            debt = Debt(mins = 1)
        )

    override fun visitWhenExpression(expression: KtWhenExpression) {
        super.visitWhenExpression(expression)

        expression.subjectExpression ?: return

        val elseText = expression.elseExpression?.text ?: return
        val entryWithSameText = expression.entries
            .firstOrNull { !it.isElse && it.expression?.text == elseText }
            ?: return

        report(
            CodeSmell(
                Issue(
                    id = ID,
                    severity = Severity.Style,
                    description = DEFAULT_DESCRIPTION,
                    debt = Debt(mins = DEBT_MIN)
                ),
                Entity.from(expression),
                when (expression.entries.count() == 2) {
                    true -> "All branches has same expression"
                    else -> "You can remove branch: '${entryWithSameText.text}'." +
                            " Else branch is enough."
                }
            )
        )
    }

    companion object {
        private const val ID = "RedundantWhenExpression"
        private const val DEFAULT_DESCRIPTION = "'when' can be simplified"
        private const val DEBT_MIN = 2
    }

}
