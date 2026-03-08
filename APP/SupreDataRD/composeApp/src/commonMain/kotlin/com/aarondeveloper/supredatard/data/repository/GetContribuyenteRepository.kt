package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetContribuyenteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetVehiculoDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource

class GetContribuyenteRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun getContribuyente(apikey : String, nRNC : String): Flow<Resource<GetContribuyenteDto>> = flow {
        try {
            emit(Resource.Loading())
            val contribuyente = supreDataRDSource.getContribuyente(apiKey = apikey, nRNC = nRNC)
            emit(Resource.Success(contribuyente))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida"))
        }
    }

}
