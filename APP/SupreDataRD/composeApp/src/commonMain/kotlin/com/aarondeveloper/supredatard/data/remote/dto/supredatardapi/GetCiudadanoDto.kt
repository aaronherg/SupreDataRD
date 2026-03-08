package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class GetCiudadanoDto(
    val Validacion: String? = null,
    val Cedula: String? = null,
    val Nombres: String? = null,
    val Apellidos: String? = null,
    val FechaNacimiento: String? = null,
    val Nacionalidad: String? = null,
    val Sexo: String? = null,
    val Calle: String? = null,
    val Edificio: String? = null,
    val Piso: String? = null,
    val Apartamento: String? = null,
    val Ciudad: String? = null,
    val Municipio: String? = null,
    val Sector: String? = null,
    val EstadoCivil: String? = null,
    val Edad: Int? = null,
    val Foto: String? = null,
    val mensaje: String? = null
)
