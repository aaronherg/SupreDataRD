package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class PermisosDto(
    val Validacion: String? = null,
    val permisoId: Int? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val mensaje: String? = null
)
