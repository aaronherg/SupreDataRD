package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.AccesosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.CorreosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CorreosRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun sendCorreo(
        apiKey: String,
        permiso: String,
        metodo: String,
        smtpHost: String,
        smtpUsername: String,
        smtpPassword: String,
        smtpPort: String,
        asunto: String,
        mensaje: String,
        piePagina: String,
        destinatario: String,
        nombreEmpresa: String,
        direccionEmpresa: String
    ): Flow<Resource<CorreosDto>> = flow {
        try {
            emit(Resource.Loading())

            val correo = supreDataRDSource.sendCorreo(
                apiKey = apiKey,
                permiso = permiso,
                metodo = metodo,
                smtpHost = smtpHost,
                smtpUsername = smtpUsername,
                smtpPassword = smtpPassword,
                smtpPort = smtpPort,
                asunto = asunto,
                mensaje = mensaje,
                piePagina = piePagina,
                destinatario = destinatario,
                nombreEmpresa = nombreEmpresa,
                direccionEmpresa = direccionEmpresa
            )

            emit(Resource.Success(correo))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }


}
