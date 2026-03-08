package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.CodigosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CodigosRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String, descripcion: String, expiracion: String, estado: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val resultado = supreDataRDSource.addCodigo(
                apiKey,
                permiso,
                metodo,
                codigo,
                descripcion,
                expiracion,
                estado
            )
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String, codigo: String, descripcion: String, expiracion: String, estado: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val resultado = supreDataRDSource.updateCodigo(
                apiKey,
                permiso,
                metodo,
                codigoId,
                codigo,
                descripcion,
                expiracion,
                estado
            )
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val resultado = supreDataRDSource.deleteCodigo(apiKey, permiso, metodo, codigoId)
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getCodigoById(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val resultado = supreDataRDSource.getCodigoById(apiKey, permiso, metodo, codigoId)
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllCodigos(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<CodigosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val resultado = supreDataRDSource.getAllCodigos(apiKey, permiso, metodo)
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun verificarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val response = supreDataRDSource.verificarCodigo(apiKey, permiso, metodo, codigo)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun usarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ): Flow<Resource<CodigosDto>> = flow {
        try {
            emit(Resource.Loading())
            val response = supreDataRDSource.usarCodigo(apiKey, permiso, metodo, codigo)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}