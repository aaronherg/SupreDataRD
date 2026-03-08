package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class GetFotoDto(
    val Validacion: String? = null,
    val cedula: String? = null,
    val foto: String? = null,
    val mensaje: String? = null
)
