package com.aarondeveloper.supredatard.presentation.screens.main.client.home

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

class HomeClientViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val configuracionRepository: ConfiguracionRepository,
    private val herramientasRepository: HerramientasRepository,
    private val suscripcionesRepository: SuscripcionesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    init {

    }

    fun getAllHerramientas() {
        viewModelScope.launch {

            herramientasRepository.getAllHerramientas(APIKEY, "listar_herramientas", "getAll")
                .collect { responseHerramientas ->
                    when (responseHerramientas) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(errorMessage = null, cargando = true) }
                        }

                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(errorMessage = null, listaHerramientasDto = responseHerramientas.data ?: emptyList())
                            }
                            if (_uiState.value.listaHerramientasDto != null) {
                                verificarSuscripcion()
                            } else {
                                _uiState.update { it.copy(noHerramientas = true, cargando = false) }
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


    fun verificarSuscripcion() {
        viewModelScope.launch {

            configuracionRepository.getUsuarioId().collect { responseConfiguracionUsuario ->
                when (responseConfiguracionUsuario) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(errorMessage = null, cargando = true) }
                    }

                    is Resource.Success -> {
                        val usuarioId = responseConfiguracionUsuario.data ?: "0"

                        herramientasRepository.getAllHerramientas(
                            APIKEY,
                            "listar_herramientas",
                            "getAll"
                        ).collect { responseHerramientas ->
                            when (responseHerramientas) {
                                is Resource.Loading -> {
                                    _uiState.update { it.copy(errorMessage = null, cargando = true) }
                                }

                                is Resource.Success -> {
                                    val herramientas = responseHerramientas.data ?: emptyList()

                                    if (herramientas.isEmpty()) {
                                        _uiState.update {
                                            it.copy(
                                                cargando = false,
                                                noHerramientas = true,
                                                listaHerramientasDto = emptyList(),
                                                listaHerramientasEstado = emptyList()
                                            )
                                        }
                                        return@collect
                                    }

                                    val herramientasConIcono = herramientas.map { herramienta ->
                                        val iconoUrl =
                                            "http://supredatard.x10.mx/archivos/imagenes/imagenes/${herramienta.icono ?: "default.png"}"
                                        herramienta.copy(icono = iconoUrl)
                                    }

                                    val listaHerramientasEstado = mutableListOf<HerramientaEstado>()
                                    val totalHerramientas = herramientasConIcono.size
                                    var herramientasProcesadas = 0

                                    _uiState.update {
                                        it.copy(listaHerramientasDto = herramientasConIcono)
                                    }

                                    herramientasConIcono.forEach { herramienta ->
                                        val herramientaId = herramienta.herramientaId ?: "0"

                                        suscripcionesRepository.checkSuscripcion(
                                            APIKEY,
                                            "verificar_suscripcion",
                                            "check",
                                            usuarioId.toString(),
                                            herramientaId.toString()
                                        ).collect { responseSuscripcion ->
                                            when (responseSuscripcion) {
                                                is Resource.Loading -> {
                                                    _uiState.update { it.copy(errorMessage = null, cargando = true) }
                                                }

                                                is Resource.Success -> {
                                                    herramientasProcesadas++

                                                    val suscrito = responseSuscripcion.data?.suscrito ?: "false"

                                                    listaHerramientasEstado.add(
                                                        HerramientaEstado(
                                                            herramientaId = herramientaId.toString(),
                                                            suscrito = suscrito
                                                        )
                                                    )

                                                    if (herramientasProcesadas == totalHerramientas) {
                                                        _uiState.update {
                                                            it.copy(
                                                                noHerramientas = false,
                                                                cargando = false,
                                                                listaHerramientasEstado = listaHerramientasEstado
                                                            )
                                                        }
                                                    }
                                                }

                                                is Resource.Error -> {
                                                    herramientasProcesadas++

                                                    if (herramientasProcesadas == totalHerramientas) {
                                                        _uiState.update {
                                                            it.copy(
                                                                cargando = false,
                                                                noHerramientas = false,
                                                                listaHerramientasEstado = listaHerramientasEstado
                                                            )
                                                        }
                                                    }

                                                    Notificacion(
                                                        "Error",
                                                        "No se pudo verificar la suscripción de una herramienta.",
                                                        NotificationType.Error
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            errorMessage = "Error al obtener herramientas",
                                            cargando = false
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = "No se pudo obtener ID de usuario",
                                cargando = false
                            )
                        }
                    }
                }
            }
        }
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
                                                            errorMessage = null
                                                        )
                                                    }

                                                    if (responseToken.data!!.mensaje != "true") {
                                                        onNoAutenticado()
                                                    }
                                                    else{
                                                        getAllHerramientas()
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

    fun onCerrarSesionClick(onSalirClick: () -> Unit) = viewModelScope.launch {
        cerrarSesion(onSalirClick)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val listaHerramientasDto: List<HerramientasDto>? = null,
    val listaHerramientasEstado: List<HerramientaEstado>? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null,
    val correo: String? = null,
    val contrasena: String? = null,
    val noHerramientas: Boolean = true,
)

data class HerramientaEstado(
    val herramientaId: String,
    val suscrito: String
)
