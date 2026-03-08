package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class AccesosDto(
    val Validacion: String? = null,
    val accesoId: Int? = null,
    val rolId: Int? = null,
    val permisoId: Int? = null,
    val mensaje: String? = null
)
