package com.aarondeveloper.supredatard.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Configuraciones")
data class ConfiguracionEntity(
    @PrimaryKey
    val usuarioId: Int = 0,
    val token: String
)
