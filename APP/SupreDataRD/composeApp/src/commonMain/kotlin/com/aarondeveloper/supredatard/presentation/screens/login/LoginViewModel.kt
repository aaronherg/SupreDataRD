package com.aarondeveloper.supredatard.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.librery.APIKEY
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.librery.logDebug
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val configuracionRepository: ConfiguracionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun validarCampos(): Boolean {
        return !_uiState.value.correo.isNullOrBlank() &&
                !_uiState.value.contrasena.isNullOrBlank()
    }

    private fun iniciarSesion(onAutenticadoMainAdmin: () -> Unit, onAutenticadoMainClient: () -> Unit) {
        viewModelScope.launch {

            if (!validarCampos()) {
                Notificacion(
                    "Advertencia",
                    "Ingrese su Correo Electrónico o Contraseña",
                    NotificationType.Advertencia
                )
                return@launch
            }

            usuariosRepository.iniciarSesion(
                APIKEY,
                "iniciar_sesion",
                "IniciarSesion",
                _uiState.value.correo!!,
                _uiState.value.contrasena!!
            ).collect { resultIniciarSesion ->
                when (resultIniciarSesion) {

                    is Resource.Loading -> {
                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                    }

                    is Resource.Success -> {

                        _uiState.update {
                            it.copy(
                                errorMessage = null,
                                cargando = false,
                                usuarioDto = resultIniciarSesion.data
                            )
                        }

                        val usuarioId =
                            _uiState.value.usuarioDto!!.usuarioId?.toString()?.toIntOrNull() ?: 0
                        val token = _uiState.value.usuarioDto!!.token.toString()
                        val rolId = _uiState.value.usuarioDto!!.rolId.toString()

                        if (usuarioId != 0 && token != "") {
                            configuracionRepository.updateUsuarioId(usuarioId).collect { responseConfiguracionUsuario ->
                                when (responseConfiguracionUsuario) {
                                    is Resource.Loading -> {
                                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                                    }
                                    is Resource.Success -> {
                                        _uiState.update { it.copy(errorMessage = null, cargando = false) }

                                        configuracionRepository.updateToken(token).collect { responseConfiguracionToken ->
                                            when (responseConfiguracionToken) {
                                                is Resource.Loading -> {
                                                    _uiState.update { it.copy(errorMessage = null, cargando = true) }
                                                }
                                                is Resource.Success -> {
                                                    _uiState.update { it.copy(errorMessage = null, cargando = false) }

                                                    if (rolId == "1") {
                                                        onAutenticadoMainAdmin()
                                                    }
                                                    else if(rolId == "2"){
                                                        onAutenticadoMainClient()
                                                    }
                                                    else {
                                                        Notificacion(
                                                            "Error",
                                                            "No se pudo determinar a que rol perteneces.",
                                                            NotificationType.Error
                                                        )
                                                    }

                                                    return@collect
                                                }
                                                is Resource.Error -> {
                                                    _uiState.update { it.copy(errorMessage = "", cargando = false) }
                                                    Notificacion(
                                                        "Error",
                                                        "No se pudo actualizar el token",
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
                                            "No se pudo actualizar ID de usuario",
                                            NotificationType.Error
                                        )
                                    }
                                }
                            }
                        } else {
                            _uiState.update { it.copy(errorMessage = "NO SE PUDO AUTENTICAR", cargando = false) }
                            Notificacion(
                                "Advertencia",
                                "Correo Electronico o Contraseña Incorrecta",
                                NotificationType.Advertencia
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(errorMessage = "", cargando = false) }
                        logDebug(message = "Error iniciar sesión: ${resultIniciarSesion.data!!.mensaje}")
                        Notificacion(
                            "Error",
                            "Error al iniciar sesión: ${resultIniciarSesion.data!!.mensaje ?: "Desconocido"}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun verificarAutenticacion(onAutenticadoMainClient: () -> Unit, onAutenticadoMainAdmin: () -> Unit) {
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
                                                                        Notificacion(
                                                                            "Error",
                                                                            "No se pudo determinar a que rol perteneces.",
                                                                            NotificationType.Error
                                                                        )
                                                                    }
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

    fun onCorreoChange(correo: String?) {
        _uiState.update { it.copy(correo = correo.toString()) }
    }

    fun onContrasenaChange(contrasena: String?) {
        _uiState.update { it.copy(contrasena = contrasena.toString()) }
    }

    fun onOlvideContrasenaClick(onNavOlvidasteContrasenaClick: () -> Unit) = viewModelScope.launch {
        onNavOlvidasteContrasenaClick()
    }

    fun onRegistrarseClick(onNavRegistrarseClick: () -> Unit) = viewModelScope.launch {
        onNavRegistrarseClick()
    }

    fun onIniciarSesionClick(onAutenticadoMainAdmin: () -> Unit, onAutenticadoMainClient: () -> Unit) = viewModelScope.launch {
        iniciarSesion(onAutenticadoMainAdmin, onAutenticadoMainClient)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null,
    val correo: String? = null,
    val contrasena: String? = null,
)
