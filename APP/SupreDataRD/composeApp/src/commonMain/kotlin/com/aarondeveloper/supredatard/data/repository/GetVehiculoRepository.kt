package com.aarondeveloper.supredatard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetVehiculoDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource

class GetVehiculoRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun getVehiculo(apikey : String, nPlaca : String): Flow<Resource<GetVehiculoDto>> = flow {
        try {
            emit(Resource.Loading())
            val vehiculo = supreDataRDSource.getVehiculo(apiKey = apikey, nPlaca = nPlaca)
            emit(Resource.Success(vehiculo))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida"))
        }
    }

}
