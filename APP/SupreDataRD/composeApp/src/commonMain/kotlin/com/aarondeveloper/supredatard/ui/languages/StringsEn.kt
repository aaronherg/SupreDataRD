package org.aarondeveloper.dealerpos.ui.languages

import kotlinx.serialization.Serializable

@Serializable
data class StringsEn(
    override val bienvenidos_a: String = "WELCOME TO",

    ) : Language
