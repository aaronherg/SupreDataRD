package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.AccesosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccesosRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addAcceso(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, permiteId: String
    ): Flow<Resource<AccesosDto>> = flow {
        try {
            emit(Resource.Loading())
            val acceso = supreDataRDSource.addAcceso(apiKey, permiso, metodo, rolId, permiteId)
            emit(Resource.Success(acceso))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String, rolId: String, permiteId: String
    ): Flow<Resource<AccesosDto>> = flow {
        try {
            emit(Resource.Loading())
            val acceso = supreDataRDSource.updateAcceso(apiKey, permiso, metodo, accesoId, rolId, permiteId)
            emit(Resource.Success(acceso))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ): Flow<Resource<AccesosDto>> = flow {
        try {
            emit(Resource.Loading())
            val acceso = supreDataRDSource.deleteAcceso(apiKey, permiso, metodo, accesoId)
            emit(Resource.Success(acceso))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAccesoById(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ): Flow<Resource<AccesosDto>> = flow {
        try {
            emit(Resource.Loading())
            val acceso = supreDataRDSource.getAccesoById(apiKey, permiso, metodo, accesoId)
            emit(Resource.Success(acceso))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllAccesos(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<AccesosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val accesos = supreDataRDSource.getAllAccesos(apiKey, permiso, metodo)
            emit(Resource.Success(accesos))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
