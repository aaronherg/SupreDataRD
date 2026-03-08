package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class HerramientasDto(
    val Validacion: String? = null,
    val herramientaId: Int? = null,
    val icono: String? = null,
    val nombre: String? = null,
    val creador: String? = null,
    val estado: String? = null,
    val mensaje: String? = null
)
