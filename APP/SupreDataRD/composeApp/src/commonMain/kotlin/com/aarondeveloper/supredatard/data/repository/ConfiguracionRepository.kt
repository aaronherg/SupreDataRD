package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.local.dao.ConfiguracionDao
import com.aarondeveloper.supredatard.data.local.entities.ConfiguracionEntity
import com.aarondeveloper.supredatard.librery.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfiguracionRepository(
    private val configuracionDao: ConfiguracionDao
) {

    fun getUsuarioId(): Flow<Resource<Int>> = flow {
        emit(Resource.Loading())
        try {
            val usuarioId = configuracionDao.getUsuarioId()
            emit(Resource.Success(usuarioId))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener el usuarioId"))
        }
    }

    fun getToken(): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val token = configuracionDao.getToken()
            emit(Resource.Success(token))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener el token"))
        }
    }

    fun updateUsuarioId(usuarioId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            configuracionDao.updateUsuarioId(usuarioId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Error al actualizar el token"))
        }
    }

    fun updateToken(token: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            configuracionDao.updateToken(token)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Error al actualizar el token"))
        }
    }

    fun getAllConfiguraciones(): Flow<Resource<ConfiguracionEntity?>> = flow {
        emit(Resource.Loading())
        try {
            val configuracion = configuracionDao.getAll()
            emit(Resource.Success(configuracion))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener todas las configuraciones"))
        }
    }

    fun saveConfiguracion(configuracion: ConfiguracionEntity): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            configuracionDao.save(configuracion)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("Error al guardar la configuración"))
        }
    }
}
