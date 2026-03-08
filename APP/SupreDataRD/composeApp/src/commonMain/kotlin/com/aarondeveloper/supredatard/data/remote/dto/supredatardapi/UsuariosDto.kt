package com.aarondeveloper.supredatard.data.remote.dto.supredatardapi

import kotlinx.serialization.Serializable

@Serializable
data class UsuariosDto(
    val Validacion: String? = null,
    val usuarioId: Int? = null,
    val rolId: Int? = null,
    val cedula: String? = null,
    val correo: String? = null,
    val contrasena: String? = null,
    val nombres: String? = null,
    val apellidos: String? = null,
    val direccion: String? = null,
    val sexo: String? = null,
    val foto: String? = null,
    val apikey: String? = null,
    val token: String? = null,
    val mensaje: String? = null
)