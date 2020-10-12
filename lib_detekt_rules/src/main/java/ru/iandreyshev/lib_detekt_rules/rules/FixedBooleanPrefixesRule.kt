package ru.iandreyshev.lib_detekt_rules.rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.com.intellij.psi.PsiFile
import org.jetbrains.kotlin.js.descriptorUtils.getJetTypeFqName
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.BindingContextUtils
import org.jetbrains.kotlin.resolve.typeBinding.createTypeBindingForReturnType

class FixedBooleanPrefixesRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue
        get() = Issue(
            id = ID,
            description = DEFAULT_DESCRIPTION,
            severity = Severity.Warning,
            debt = Debt(mins = 1)
        )

    override fun visitFile(file: PsiFile?) {
        super.visitFile(file)
        println(file?.name)
    }

    override fun visitNamedDeclaration(declaration: KtNamedDeclaration) {
        println("visitNamedDeclaration")
        if (declaration.nameAsSafeName.isSpecial) {
            return
        }
        declaration.nameIdentifier?.parent?.javaClass?.let {
            when (declaration) {
                is KtProperty -> handleProperty(declaration)
            }
        }
        super.visitNamedDeclaration(declaration)
    }

    private fun handleProperty(property: KtProperty) {
        if (bindingContext == BindingContext.EMPTY) {
            println("Binding context is empty")
            return
        }

        val type = BindingContextUtils.getTypeNotNull(bindingContext, property)
        print(type)

        val typeName = getTypeName(property)

        if (!property.isBoolean() || property.isPrefixValid()) {
            return
        }

        report(
            CodeSmell(
                Issue(
                    id = ID,
                    severity = Severity.Style,
                    description = DEFAULT_DESCRIPTION,
                    debt = Debt(mins = 1)
                ),
                Entity.from(property),
                "Property name ${property.name} is invalid." +
                        " Use one of available prefixes: $availablePrefixesStr." +
                        "Typename: $typeName"
            )
        )
    }

    private fun KtProperty.isBoolean(): Boolean {
        val typeName = getTypeName(this)
        print(typeName)
        return typeName == KT_BOOLEAN || typeName == JAVA_BOOLEAN
    }

    private fun getTypeName(parameter: KtCallableDeclaration): String? {
        return parameter.createTypeBindingForReturnType(bindingContext)
            ?.type
            ?.getJetTypeFqName(false)
    }

    private fun KtProperty.isPrefixValid(): Boolean {
        val prefix = name?.takeWhile { it.isLowerCase() }
        return prefix in validPrefixes
    }

    companion object {
        private val validPrefixes = setOf("is", "has", "contains")

        private val availablePrefixesStr = validPrefixes.joinToString()

        private const val ID = "FixedBooleanPrefixesRule"
        private const val DEFAULT_DESCRIPTION = "Invalid boolean property prefix"

        private const val KT_BOOLEAN = "kotlin.Boolean"
        private const val JAVA_BOOLEAN = "java.lang.Boolean"
    }

}
