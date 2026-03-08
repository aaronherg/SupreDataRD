package com.aarondeveloper.supredatard.presentation.screens.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.local.entities.ConfiguracionEntity
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.librery.APIKEY
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class IntroViewModel(
    private val configuracionRepository: ConfiguracionRepository,
    private val usuariosRepository: UsuariosRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        verificarConfiguracion()
    }

    private fun verificarConfiguracion() {
        viewModelScope.launch {
            configuracionRepository.getAllConfiguraciones().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(cargando = false) }

                        if (result.data == null) {
                            val defaultConfig = ConfiguracionEntity(usuarioId = 0, token = "")
                            configuracionRepository.saveConfiguracion(defaultConfig)
                                .collect { resultSaveConfig ->
                                    when (resultSaveConfig) {
                                        is Resource.Loading -> {
                                            _uiState.update { it.copy(cargando = true) }
                                        }

                                        is Resource.Success -> {
                                            _uiState.update { it.copy(cargando = false) }
                                        }

                                        is Resource.Error -> {
                                            _uiState.update { it.copy(cargando = false) }
                                            Notificacion(
                                                "Error",
                                                "No se pudo guardar la configuración por defecto",
                                                NotificationType.Error
                                            )
                                        }
                                    }
                                }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(cargando = false) }
                        Notificacion(
                            "Error",
                            "Error al obtener configuración: ${result.message ?: "Desconocido"}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun verificarAutenticacion(onAutenticadoMainClient: () -> Unit, onAutenticadoMainAdmin: () -> Unit, onNavBitacoraClick: () -> Unit) {
        viewModelScope.launch {
            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(cargando = false) }
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

                        configuracionRepository.getToken().collect { responseConfiguracionToken ->
                            when (responseConfiguracionToken) {
                                is Resource.Loading -> {
                                    _uiState.update { it.copy(cargando = true) }
                                }

                                is Resource.Success -> {
                                    _uiState.update { it.copy(cargando = false) }
                                    val token = responseConfiguracionToken.data ?: ""

                                    usuariosRepository.verificarToken(
                                        APIKEY,
                                        "verificar_token",
                                        "VerificarToken",
                                        usuarioId.toString(),
                                        token
                                    ).collect { responseToken ->
                                        when (responseToken) {
                                            is Resource.Loading -> {
                                                _uiState.update { it.copy(cargando = true) }
                                            }

                                            is Resource.Success -> {
                                                if (responseToken.data!!.mensaje == "true") {

                                                    usuariosRepository.getUsuarioNoFotoById(
                                                        APIKEY,
                                                        "buscar_usuario",
                                                        "getNoFotoId",
                                                        usuarioId.toString()
                                                    ).collect { responseUsuarioById ->
                                                        when (responseUsuarioById) {
                                                            is Resource.Loading -> {
                                                                _uiState.update { it.copy(cargando = true) }
                                                            }

                                                            is Resource.Success -> {
                                                                _uiState.update { it.copy(cargando = false) }
                                                                if (responseUsuarioById.data!!.Validacion == "Exitoso") {

                                                                    val rolId = responseUsuarioById.data.rolId.toString()

                                                                    if (rolId == "1") {
                                                                        onAutenticadoMainAdmin()
                                                                    }
                                                                    else if(rolId == "2"){
                                                                        onAutenticadoMainClient()
                                                                    }
                                                                    else {
                                                                        onNavBitacoraClick()
                                                                        Notificacion(
                                                                            "Error",
                                                                            "No se pudo determinar a que rol perteneces.",
                                                                            NotificationType.Error
                                                                        )
                                                                    }
                                                                }
                                                                else {
                                                                    onNavBitacoraClick()
                                                                }
                                                            }

                                                            is Resource.Error -> {
                                                                _uiState.update { it.copy(cargando = false) }
                                                                Notificacion(
                                                                    "Error",
                                                                    "Error al verificar token: ${responseUsuarioById.message ?: "Desconocido"}",
                                                                    NotificationType.Error
                                                                )
                                                            }
                                                        }
                                                    }

                                                } else {
                                                    _uiState.update { it.copy(cargando = false) }
                                                    onNavBitacoraClick()
                                                }
                                            }

                                            is Resource.Error -> {
                                                _uiState.update { it.copy(cargando = false) }
                                                Notificacion(
                                                    "Error",
                                                    "Error al verificar token: ${responseToken.message ?: "Desconocido"}",
                                                    NotificationType.Error
                                                )
                                            }
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _uiState.update { it.copy(cargando = false) }
                                    Notificacion(
                                        "Error",
                                        "No se pudo obtener token",
                                        NotificationType.Error
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(cargando = false) }
                        Notificacion(
                            "Error",
                            "No se pudo obtener ID de usuario",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun onBitacoraClick(onAutenticadoMainClient: () -> Unit, onAutenticadoMainAdmin: () -> Unit, onNavBitacoraClick: () -> Unit) = viewModelScope.launch {
        verificarAutenticacion(onAutenticadoMainClient, onAutenticadoMainAdmin, onNavBitacoraClick)
    }
}

data class UiState(
    val cargando: Boolean = false,
    val errorMessage: String? = null,
)
