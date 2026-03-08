package com.aarondeveloper.supredatard.presentation.screens.main.admin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.HerramientasDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.HerramientasRepository
import com.aarondeveloper.supredatard.data.repository.SuscripcionesRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.librery.APIKEY
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeAdminViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val configuracionRepository: ConfiguracionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    init {

    }


    fun verificarAutenticacion(onNoAutenticado: () -> Unit) {
        viewModelScope.launch {

            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null) }
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

                        configuracionRepository.getToken()
                            .collect { responseConfiguracionToken ->
                                when (responseConfiguracionToken) {
                                    is Resource.Loading -> {
                                        _uiState.update {
                                            it.copy(
                                                errorMessage = null,
                                                cargando = true
                                            )
                                        }
                                    }

                                    is Resource.Success -> {
                                        _uiState.update {
                                            it.copy(
                                                errorMessage = null
                                            )
                                        }
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
                                                    _uiState.update {
                                                        it.copy(
                                                            errorMessage = null,
                                                            cargando = true
                                                        )
                                                    }
                                                }

                                                is Resource.Success -> {
                                                    _uiState.update {
                                                        it.copy(
                                                            errorMessage = null, cargando = false
                                                        )
                                                    }

                                                    if (responseToken.data!!.mensaje != "true") {
                                                        onNoAutenticado()
                                                    }

                                                }

                                                is Resource.Error -> {
                                                    _uiState.update {
                                                        it.copy(
                                                            errorMessage = "",
                                                            cargando = false
                                                        )
                                                    }
                                                    Notificacion(
                                                        "Error",
                                                        "Error al verificar token: ${responseToken.data!!.mensaje ?: "Desconocido"}",
                                                        NotificationType.Error
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    is Resource.Error -> {
                                        _uiState.update {
                                            it.copy(
                                                errorMessage = "",
                                                cargando = false
                                            )
                                        }
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
                        _uiState.update { it.copy(errorMessage = "", cargando = false) }
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

    fun cerrarSesion(onSalirClick: () -> Unit) {
        viewModelScope.launch {

            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null) }
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

                        usuariosRepository.cerrarSesion(
                            APIKEY,
                            "cerrar_sesion",
                            "CerrarSession",
                            usuarioId.toString()
                        ).collect { responseUsuario ->
                            when (responseUsuario) {
                                is Resource.Loading -> {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = null,
                                            cargando = true
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = null,
                                            cargando = false
                                        )
                                    }

                                    if(responseUsuario.data!!.mensaje == "true"){
                                        Notificacion(
                                            "Exitoso",
                                            "Espero regreses pronto :)",
                                            NotificationType.Exitoso
                                        )
                                        onSalirClick()
                                    }
                                    else{
                                        Notificacion(
                                            "Error",
                                            "Se ha detectado un error para cerrar sesion",
                                            NotificationType.Error
                                        )
                                    }

                                }

                                is Resource.Error -> {
                                    _uiState.update { it.copy(errorMessage = "", cargando = false) }
                                    Notificacion(
                                        "Error",
                                        "No se pudo obtener ID de usuario",
                                        NotificationType.Error
                                    )
                                }
                            }
                        }

                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = "", cargando = false) }
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

    fun onNavHerramientasClick() {
        _uiState.update {
            it.copy(
                noNavHerramientas = true,
                onNavInicio = false,
                noNavSuscripciones = false
            )
        }
    }

    fun onNavInicioClick() {
        _uiState.update {
            it.copy(
                noNavHerramientas = false,
                onNavInicio = true,
                noNavSuscripciones = false
            )
        }
    }

    fun onNavSuscripcionesClick() {
        _uiState.update {
            it.copy(
                noNavHerramientas = false,
                onNavInicio = false,
                noNavSuscripciones = true
            )
        }
    }


    fun onCerrarSesionClick(onSalirClick: () -> Unit) = viewModelScope.launch {
        cerrarSesion(onSalirClick)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null,
    val noNavHerramientas: Boolean = false,
    val onNavInicio: Boolean = false,
    val noNavSuscripciones: Boolean = false,
)

