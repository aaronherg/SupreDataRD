package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class GetContribuyenteDto(
    val Validacion: String? = null,
    val RNC: String? = null,
    val razonSocial: String? = null,
    val nombreComercial: String? = null,
    val categoria: String? = null,
    val regimenPagos: String? = null,
    val estado: String? = null,
    val actividadEconomica: String? = null,
    val administracionLocal: String? = null,
    val facturadorElectronico: String? = null,
    val licenciasVHM: String? = null,
    val mensaje: String? = null
)