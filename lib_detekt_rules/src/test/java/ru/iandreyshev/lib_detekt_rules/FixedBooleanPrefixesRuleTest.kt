package ru.iandreyshev.lib_detekt_rules

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import org.intellij.lang.annotations.Language
import ru.iandreyshev.lib_detekt_rules.rules.FixedBooleanPrefixesRule

class FixedBooleanPrefixesRuleTest : FreeSpec({

    val rule = FixedBooleanPrefixesRule()

    "Should not lint Int properties" {
        @Language("kt")
        val code = """
            fun main() {
                val issBoolean: Boolean = false
            }
        """.trimIndent()

        rule.lint(code).shouldBeEmpty()
    }

})
