package com.linhtetko.monthly.ui.locale.language

import kotlinx.serialization.Serializable

@Serializable
data class LanguageType(
    val code: String,
    val name: String
)

@Serializable
data class LanguageBundle(
    val type: LanguageType,
    val language: Language,
)

@Serializable
data class LanguageBundles(
    val bundles: List<LanguageBundle>
) {
    fun mapper(code: String): LanguageBundle? = bundles.find { it.type.code == code }
}
