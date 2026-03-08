package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.HerramientasDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HerramientasRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addHerramienta(
        apiKey: String, permiso: String, metodo: String,
        icono: String, nombre: String, creador: String, estado: String
    ): Flow<Resource<HerramientasDto>> = flow {
        try {
            emit(Resource.Loading())
            val herramienta = supreDataRDSource.addHerramienta(apiKey, permiso, metodo, icono, nombre, creador, estado)
            emit(Resource.Success(herramienta))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, icono: String, nombre: String, creador: String, estado: String
    ): Flow<Resource<HerramientasDto>> = flow {
        try {
            emit(Resource.Loading())
            val herramienta = supreDataRDSource.updateHerramienta(apiKey, permiso, metodo, herramientaId, icono, nombre, creador, estado)
            emit(Resource.Success(herramienta))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ): Flow<Resource<HerramientasDto>> = flow {
        try {
            emit(Resource.Loading())
            val herramienta = supreDataRDSource.deleteHerramienta(apiKey, permiso, metodo, herramientaId)
            emit(Resource.Success(herramienta))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getHerramientaById(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ): Flow<Resource<HerramientasDto>> = flow {
        try {
            emit(Resource.Loading())
            val herramienta = supreDataRDSource.getHerramientaById(apiKey, permiso, metodo, herramientaId)
            emit(Resource.Success(herramienta))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllHerramientas(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<HerramientasDto>>> = flow {
        try {
            emit(Resource.Loading())
            val herramientas = supreDataRDSource.getAllHerramientas(apiKey, permiso, metodo)
            emit(Resource.Success(herramientas))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
