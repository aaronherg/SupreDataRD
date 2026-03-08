package com.aarondeveloper.supredatard.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.aarondeveloper.supredatard.data.local.entities.ConfiguracionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfiguracionDao {

    @Upsert
    suspend fun save(configuracion: ConfiguracionEntity)

    @Query("SELECT usuarioId FROM Configuraciones LIMIT 1")
    suspend fun getUsuarioId(): Int

    @Query("UPDATE Configuraciones SET usuarioId = :usuarioId")
    suspend fun updateUsuarioId(usuarioId: Int)

    @Query("SELECT token FROM Configuraciones LIMIT 1")
    suspend fun getToken(): String

    @Query("UPDATE Configuraciones SET token = :token")
    suspend fun updateToken(token: String)

    @Delete
    suspend fun delete(configuracion: ConfiguracionEntity)

    @Query("SELECT * FROM Configuraciones LIMIT 1")
    suspend fun getAll(): ConfiguracionEntity?

}

