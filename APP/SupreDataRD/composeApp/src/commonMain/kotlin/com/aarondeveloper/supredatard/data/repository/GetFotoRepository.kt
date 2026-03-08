package com.aarondeveloper.supredatard.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetCiudadanoDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetFotoDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource

class GetFotoRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun getFoto(apikey : String, nCedula : String): Flow<Resource<GetFotoDto>> = flow {
        try {
            emit(Resource.Loading())
            val foto = supreDataRDSource.getFoto(apiKey = apikey, nCedula = nCedula)
            emit(Resource.Success(foto))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida"))
        }
    }

}
