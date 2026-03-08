package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.SuscripcionesDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SuscripcionesRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, usuarioId: String, pagoId: String,
        descripcion: String, fechaInicio: String, fechaFin: String
    ): Flow<Resource<SuscripcionesDto>> = flow {
        try {
            emit(Resource.Loading())
            val suscripcion = supreDataRDSource.addSuscripcion(apiKey, permiso, metodo, herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin)
            emit(Resource.Success(suscripcion))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String, herramientaId: String, usuarioId: String,
        pagoId: String, descripcion: String, fechaInicio: String, fechaFin: String
    ): Flow<Resource<SuscripcionesDto>> = flow {
        try {
            emit(Resource.Loading())
            val suscripcion = supreDataRDSource.updateSuscripcion(apiKey, permiso, metodo, suscripcionId, herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin)
            emit(Resource.Success(suscripcion))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ): Flow<Resource<SuscripcionesDto>> = flow {
        try {
            emit(Resource.Loading())
            val suscripcion = supreDataRDSource.deleteSuscripcion(apiKey, permiso, metodo, suscripcionId)
            emit(Resource.Success(suscripcion))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getSuscripcionById(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ): Flow<Resource<SuscripcionesDto>> = flow {
        try {
            emit(Resource.Loading())
            val suscripcion = supreDataRDSource.getSuscripcionById(apiKey, permiso, metodo, suscripcionId)
            emit(Resource.Success(suscripcion))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllSuscripciones(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<SuscripcionesDto>>> = flow {
        try {
            emit(Resource.Loading())
            val suscripciones = supreDataRDSource.getAllSuscripciones(apiKey, permiso, metodo)
            emit(Resource.Success(suscripciones))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun checkSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, herramientaId: String
    ): Flow<Resource<SuscripcionesDto>> = flow {
        try {
            emit(Resource.Loading())
            val suscripcion = supreDataRDSource.checkSuscripcion(apiKey, permiso, metodo, usuarioId, herramientaId)
            emit(Resource.Success(suscripcion))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

}
