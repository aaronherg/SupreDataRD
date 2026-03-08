package com.aarondeveloper.supredatard.presentation.screens.tools.getvehiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetLicenciaDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetVehiculoDto
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.GetVehiculoRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.librery.APIKEY
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GetVehiculoViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val configuracionRepository: ConfiguracionRepository,
    private val getVehiculoRepository: GetVehiculoRepository,
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
                                                            errorMessage = null,
                                                            cargando = false
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

    fun buscarVehiculo(){

        viewModelScope.launch {
            _uiState.update { it.copy(ocultarTeclado = true) }
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

                                        val apikey = responseUsuarioById.data.apikey.toString()
                                        val nPlaca = _uiState.value.nPlaca.toString()

                                        if (!nPlaca.isNullOrBlank()) {
                                            _uiState.update { it.copy(ocultarTeclado = true, encontrado = false) }

                                            getVehiculoRepository.getVehiculo(apikey, nPlaca).collect { responseGetVehiculo ->
                                                when (responseGetVehiculo) {
                                                    is Resource.Loading -> {
                                                        _uiState.update { it.copy(cargando = true) }
                                                    }

                                                    is Resource.Success -> {
                                                        _uiState.update { it.copy(cargando = false) }
                                                        if(responseGetVehiculo.data!!.Validacion.toString() == "Exitoso"){

                                                            val placa = responseGetVehiculo.data.Placa.toString()

                                                            if(!placa.isNullOrBlank()){
                                                                _uiState.update { it.copy(nPlaca = "", getVehiculoDto = responseGetVehiculo.data, encontrado = true) }
                                                            }
                                                            else{
                                                                Notificacion(
                                                                    "Advertencia",
                                                                    "No se encontraron datos relacionados a este vehiculo ${nPlaca}",
                                                                    NotificationType.Advertencia
                                                                )
                                                            }
                                                        }
                                                        else{
                                                            Notificacion(
                                                                "Advertencia",
                                                                "No se encontraron datos relacionados a este vehiculo ${nPlaca}",
                                                                NotificationType.Advertencia
                                                            )
                                                        }
                                                    }

                                                    is Resource.Error -> {
                                                        _uiState.update { it.copy(cargando = false) }
                                                        Notificacion(
                                                            "Error",
                                                            "Error: ${responseGetVehiculo.message.toString()}",
                                                            NotificationType.Error
                                                        )
                                                    }
                                                }
                                            }

                                        }
                                        else{

                                            Notificacion(
                                                "Advertencia",
                                                "Su numero de placa ${nPlaca} no es valida.",
                                                NotificationType.Advertencia
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

    fun onPlacaChange(placa: String?) {
        _uiState.update { it.copy(ocultarTeclado = false) }
        val nuevaPlaca = placa?.uppercase() ?: ""
        _uiState.update { it.copy(nPlaca = nuevaPlaca) }
    }
    fun onBuscarVehiculoClick() = viewModelScope.launch {
        buscarVehiculo()
    }

    fun onCerrarSesionClick(onSalirClick: () -> Unit) = viewModelScope.launch {
        cerrarSesion(onSalirClick)
    }

}

data class UiState(
    val getVehiculoDto: GetVehiculoDto? = null,
    val nPlaca: String? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null,
    val ocultarTeclado: Boolean = false,
    val encontrado: Boolean = false
)
