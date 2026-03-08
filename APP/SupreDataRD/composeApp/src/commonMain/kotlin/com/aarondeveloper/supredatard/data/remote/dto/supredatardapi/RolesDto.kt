package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class RolesDto(
    val Validacion: String? = null,
    val rolId: Int? = null,
    val nombre: String? = null,
    val mensaje: String? = null
)
