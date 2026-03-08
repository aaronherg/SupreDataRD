package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class PagosDto(
    val Validacion: String? = null,
    val pagoId: Int? = null,
    val usuarioId: Int? = null,
    val descripcion: String? = null,
    val metodo: String? = null,
    val monto: Double? = null,
    val fecha: String? = null,
    val mensaje: String? = null
)
