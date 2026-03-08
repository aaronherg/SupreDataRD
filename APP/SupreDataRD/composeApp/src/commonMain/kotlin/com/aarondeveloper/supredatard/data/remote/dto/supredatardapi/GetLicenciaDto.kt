package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class GetLicenciaDto(
    val Validacion: String? = null,
    val CedulaChofer: String? = null,
    val NombreChofer: String? = null,
    val CategoriaLicencia: String? = null,
    val FechaExpiracion: String? = null,
    val LicenciaVencida: String? = null,
    val TelefonoChofer: String? = null,
    val Edad: String? = null,
    val mensaje: String? = null
)