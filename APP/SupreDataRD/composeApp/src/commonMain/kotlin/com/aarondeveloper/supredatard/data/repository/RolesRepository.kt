package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.RolesDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RolesRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun addRol(
        apiKey: String, permiso: String, metodo: String,
        descripcion: String
    ): Flow<Resource<RolesDto>> = flow {
        try {
            emit(Resource.Loading())
            val rol = supreDataRDSource.addRol(apiKey, permiso, metodo, descripcion)
            emit(Resource.Success(rol))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, descripcion: String
    ): Flow<Resource<RolesDto>> = flow {
        try {
            emit(Resource.Loading())
            val rol = supreDataRDSource.updateRol(apiKey, permiso, metodo, rolId, descripcion)
            emit(Resource.Success(rol))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ): Flow<Resource<RolesDto>> = flow {
        try {
            emit(Resource.Loading())
            val rol = supreDataRDSource.deleteRol(apiKey, permiso, metodo, rolId)
            emit(Resource.Success(rol))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getRolById(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ): Flow<Resource<RolesDto>> = flow {
        try {
            emit(Resource.Loading())
            val rol = supreDataRDSource.getRolById(apiKey, permiso, metodo, rolId)
            emit(Resource.Success(rol))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllRoles(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<RolesDto>>> = flow {
        try {
            emit(Resource.Loading())
            val roles = supreDataRDSource.getAllRoles(apiKey, permiso, metodo)
            emit(Resource.Success(roles))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
