package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class CodigosDto(
    val Validacion: String? = null,
    val codigoId: Int? = null,
    val codigo: String? = null,
    val descripcion: String? = null,
    val expiracion: String? = null,
    val estado: String? = null,
    val mensaje: String? = null
)
