package ru.iandreyshev.lib_detekt_rules

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import org.intellij.lang.annotations.Language
import ru.iandreyshev.lib_detekt_rules.rules.RedundantWhenExpression

class RedundantWhenExpressionRuleTest : FreeSpec({

    val rule = RedundantWhenExpression()

    "Should lint when some branch have expression like else branch" {
        @Language("kt")
        val code = """
            fun main() {
                when (progress) {
                    Progress.NOT_STARTED -> "NOT_STARTED"
                    Progress.IN_PROGRESS -> "NOT_STARTED"
                    Progress.COMPLETED -> "COMPLETED"
                    else -> "NOT_STARTED"
                }
            }
        """.trimIndent()

        rule.lint(code).shouldNotBeEmpty()
    }

    "Should not lint when else is absent" {
        @Language("kt")
        val code = """
            fun main() {
                when (progress) {
                    Progress.NOT_STARTED -> "NOT_STARTED"
                    Progress.IN_PROGRESS -> "NOT_STARTED"
                    Progress.COMPLETED -> "COMPLETED"
                }
            }
        """.trimIndent()

        rule.lint(code).shouldBeEmpty()
    }

    "Should not lint when all branches is unique" {
        @Language("kt")
        val code = """
            fun main() {
                when (progress) {
                    Progress.NOT_STARTED -> "NOT_STARTED"
                    Progress.IN_PROGRESS -> "NOT_STARTED"
                    Progress.COMPLETED -> "COMPLETED"
                    else -> "ELSE"
                }
            }
        """.trimIndent()

        rule.lint(code).shouldBeEmpty()
    }

    "Should lint when-expression with two branches" {
        @Language("kt")
        val code = """
            fun main() {
                when (progress) {
                    Progress.NOT_STARTED -> "NOT_STARTED"
                    else -> "NOT_STARTED"
                }
            }
        """.trimIndent()

        rule.lint(code).shouldNotBeEmpty()
    }

})
