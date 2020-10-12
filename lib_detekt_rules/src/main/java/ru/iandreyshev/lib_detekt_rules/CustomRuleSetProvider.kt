package ru.iandreyshev.lib_detekt_rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import ru.iandreyshev.lib_detekt_rules.rules.FixedBooleanPrefixesRule

class CustomRuleSetProvider : RuleSetProvider {

    override val ruleSetId: String = "iandreyshev_rule_set"

    override fun instance(config: Config): RuleSet =
        RuleSet(
            id = ruleSetId,
            rules = listOf(FixedBooleanPrefixesRule(config))
        )

}
