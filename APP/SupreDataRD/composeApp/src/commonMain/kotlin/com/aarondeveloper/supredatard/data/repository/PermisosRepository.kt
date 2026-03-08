package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.PermisosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PermisosRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addPermite(
        apiKey: String, permiso: String, metodo: String,
        nombre: String, descripcion: String
    ): Flow<Resource<PermisosDto>> = flow {
        try {
            emit(Resource.Loading())
            val permite = supreDataRDSource.addPermiso(apiKey, permiso, metodo, nombre, descripcion)
            emit(Resource.Success(permite))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updatePermite(
        apiKey: String, permiso: String, metodo: String,
        permiteId: String, nombre: String, descripcion: String
    ): Flow<Resource<PermisosDto>> = flow {
        try {
            emit(Resource.Loading())
            val permite = supreDataRDSource.updatePermiso(apiKey, permiso, metodo, permiteId, nombre, descripcion)
            emit(Resource.Success(permite))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deletePermite(
        apiKey: String, permiso: String, metodo: String,
        permiteId: String
    ): Flow<Resource<PermisosDto>> = flow {
        try {
            emit(Resource.Loading())
            val permite = supreDataRDSource.deletePermiso(apiKey, permiso, metodo, permiteId)
            emit(Resource.Success(permite))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getPermiteById(
        apiKey: String, permiso: String, metodo: String,
        permiteId: String
    ): Flow<Resource<PermisosDto>> = flow {
        try {
            emit(Resource.Loading())
            val permite = supreDataRDSource.getPermisoById(apiKey, permiso, metodo, permiteId)
            emit(Resource.Success(permite))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllPermite(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<PermisosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val permiteList = supreDataRDSource.getAllPermisos(apiKey, permiso, metodo)
            emit(Resource.Success(permiteList))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
