package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class GetVehiculoDto(
    val Validacion: String? = null,
    val Placa: String? = null,
    val Chasis: String? = null,
    val Marca: String? = null,
    val Estado: String? = null,
    val SecMatricula: String? = null,
    val CapacidadCarga: String? = null,
    val RNCPropietario: String? = null,
    val Propietario: String? = null,
    val ClaseVehiculo: String? = null,
    val Modelo: String? = null,
    val Color: String? = null,
    val Ano: String? = null,
    val mensaje: String? = null
)