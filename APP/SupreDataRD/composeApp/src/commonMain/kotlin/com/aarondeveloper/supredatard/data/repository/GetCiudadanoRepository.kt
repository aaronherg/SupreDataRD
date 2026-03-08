package com.aarondeveloper.supredatard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetCiudadanoDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource

class GetCiudadanoRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun getCiudadano(apikey : String, nCedula : String): Flow<Resource<GetCiudadanoDto>> = flow {
        try {
            emit(Resource.Loading())
            val ciudadano = supreDataRDSource.getCiudadano(apiKey = apikey, nCedula = nCedula)
            emit(Resource.Success(ciudadano))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida"))
        }
    }

}
