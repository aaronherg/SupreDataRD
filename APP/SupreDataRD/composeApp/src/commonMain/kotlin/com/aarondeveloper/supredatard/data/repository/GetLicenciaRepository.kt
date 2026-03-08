package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetLicenciaDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource

class GetLicenciaRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun getLicencia(apikey : String, nCedula : String): Flow<Resource<GetLicenciaDto>> = flow {
        try {
            emit(Resource.Loading())
            val licencia = supreDataRDSource.getLicencia(apiKey = apikey, nCedula = nCedula)
            emit(Resource.Success(licencia))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida"))
        }
    }

}
