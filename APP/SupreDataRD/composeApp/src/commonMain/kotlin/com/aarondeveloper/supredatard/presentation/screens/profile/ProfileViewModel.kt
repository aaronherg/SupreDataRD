package com.aarondeveloper.supredatard.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
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

class ProfileViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val configuracionRepository: ConfiguracionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {

    }

    fun formatearCedula(cedula: String?): String {
        if (cedula.isNullOrBlank()) return "000-0000000-0"

        val soloDigitos = cedula.trim().filter { it.isDigit() }

        if (soloDigitos.length != 11) return soloDigitos

        return buildString {
            append(soloDigitos.substring(0, 3))
            append('-')
            append(soloDigitos.substring(3, 10))
            append('-')
            append(soloDigitos.substring(10))
        }
    }

    fun getUsuario(onRegresar: () -> Unit) {
        viewModelScope.launch {

            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(errorMessage = null) }
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

                        usuariosRepository.getUsuarioById(APIKEY, "buscar_usuario", "getId", usuarioId.toString()).collect { responseUsuario ->
                                when (responseUsuario) {
                                    is Resource.Loading -> {
                                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                                    }

                                    is Resource.Success -> {
                                        val usuario = responseUsuario.data

                                        val usuarioLimpio = usuario?.copy(
                                            foto = usuario.foto?.trim().takeIf { !it.isNullOrBlank() } ?: "",
                                            cedula = formatearCedula(usuario.cedula?.trim().takeIf { !it.isNullOrBlank() } ?: ""),
                                            nombres = usuario.nombres?.trim().takeIf { !it.isNullOrBlank() } ?: "Desconocido",
                                            apellidos = usuario.apellidos?.trim().takeIf { !it.isNullOrBlank() } ?: "",
                                            sexo = usuario.sexo?.trim().takeIf { !it.isNullOrBlank() } ?: "Desconocida",
                                            direccion = usuario.direccion?.trim().takeIf { !it.isNullOrBlank() } ?: "Desconocida"
                                        )

                                        _uiState.update {
                                            it.copy(errorMessage = null, usuarioDto = usuarioLimpio, cargando = false)
                                        }

                                        if (usuarioLimpio == null) {
                                            Notificacion(
                                                "Error",
                                                "No se pudo identificar tu identidad",
                                                NotificationType.Error
                                            )

                                            onRegresar()

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


    fun verificarAutenticacion(onNoAutenticado: () -> Unit, onRegresar: () -> Unit) {
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
                                                            errorMessage = null,
                                                            cargando = false
                                                        )
                                                    }

                                                    if (responseToken.data!!.mensaje != "true") {
                                                        onNoAutenticado()
                                                    }
                                                    else{
                                                        getUsuario(onRegresar)
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

    fun verificarRol(onInicioClientClick: () -> Unit, onInicioAdminClick: () -> Unit) {

        viewModelScope.launch {
            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(cargando = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update { it.copy(cargando = false) }
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

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
                                            onInicioAdminClick()
                                        }
                                        else if(rolId == "2"){
                                            onInicioClientClick()
                                        }
                                        else {
                                            Notificacion(
                                                "Error",
                                                "No se pudo determinar a que rol perteneces.",
                                                NotificationType.Error
                                            )
                                        }
                                    }
                                    else {
                                        Notificacion(
                                            "Error",
                                            "Error: ${responseUsuarioById.data.mensaje ?: "Desconocido"}",
                                            NotificationType.Error
                                        )
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

    fun onCerrarSesionClick(onSalirClick: () -> Unit) = viewModelScope.launch {
        cerrarSesion(onSalirClick)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null
)
