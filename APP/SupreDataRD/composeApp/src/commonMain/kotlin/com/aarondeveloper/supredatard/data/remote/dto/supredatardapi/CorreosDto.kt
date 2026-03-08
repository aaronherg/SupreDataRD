package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class CorreosDto(
    val Validacion: String? = null,
    val mensaje: String? = null
)
