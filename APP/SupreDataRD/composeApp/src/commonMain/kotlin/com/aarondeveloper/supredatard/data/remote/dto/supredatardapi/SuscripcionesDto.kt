package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class SuscripcionesDto(
    val Validacion: String? = null,
    val suscripcionId: Int? = null,
    val herramientaId: Int? = null,
    val usuarioId: Int? = null,
    val pagoId: Int? = null,
    val descripcion: String? = null,
    val suscrito: String? = null,
    val fechaInicio: String? = null,
    val fechaFin: String? = null,
    val mensaje: String? = null
)
