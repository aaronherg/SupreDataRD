package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.PagosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PagosRepository(private val supreDataRDSource: SupreDataRDSource) {

    fun addPago(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, descripcion: String,
        metodoPago: String, monto: String, fecha: String
    ): Flow<Resource<PagosDto>> = flow {
        try {
            emit(Resource.Loading())
            val pago = supreDataRDSource.addPago(apiKey, permiso, metodo, usuarioId, descripcion, metodoPago, monto, fecha)
            emit(Resource.Success(pago))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updatePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String, usuarioId: String, descripcion: String,
        metodoPago: String, monto: String, fecha: String
    ): Flow<Resource<PagosDto>> = flow {
        try {
            emit(Resource.Loading())
            val pago = supreDataRDSource.updatePago(apiKey, permiso, metodo, pagoId, usuarioId, descripcion, metodoPago, monto, fecha)
            emit(Resource.Success(pago))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deletePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ): Flow<Resource<PagosDto>> = flow {
        try {
            emit(Resource.Loading())
            val pago = supreDataRDSource.deletePago(apiKey, permiso, metodo, pagoId)
            emit(Resource.Success(pago))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getPagoById(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ): Flow<Resource<PagosDto>> = flow {
        try {
            emit(Resource.Loading())
            val pago = supreDataRDSource.getPagoById(apiKey, permiso, metodo, pagoId)
            emit(Resource.Success(pago))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getAllPagos(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<PagosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val pagos = supreDataRDSource.getAllPagos(apiKey, permiso, metodo)
            emit(Resource.Success(pagos))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
