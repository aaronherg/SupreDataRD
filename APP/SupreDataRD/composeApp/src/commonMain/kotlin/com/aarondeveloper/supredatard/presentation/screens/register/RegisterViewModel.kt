package com.aarondeveloper.supredatard.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetCiudadanoDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.data.repository.ConfiguracionRepository
import com.aarondeveloper.supredatard.data.repository.GetCiudadanoRepository
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

class RegisterViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val getCiudadanoRepository: GetCiudadanoRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun validarCampos() {
        val state = _uiState.value

        // -------- FASE 1: CÉDULA --------
        if (state.primeraFase) {
            if (!state.cedula.isNullOrBlank() && state.cedula.length == 11) {
                _uiState.update { it.copy(ocultarTeclado = true) }
                consultarCedula(state.cedula)
            } else {
                _uiState.update {
                    it.copy(
                        primeraFase = true,
                        segundaFase = false,
                        terceraFase = false,
                        formCompletado = false
                    )
                }
            }
            return
        }

        // -------- FASE 2: DATOS PERSONALES --------
        if (state.segundaFase) {
            val camposCompletos =
                !state.nombres.isNullOrBlank() &&
                        !state.apellidos.isNullOrBlank() &&
                        !state.sexo.isNullOrBlank() &&
                        !state.telefono.isNullOrBlank() &&
                        !state.fechaNacimiento.isNullOrBlank() &&
                        !state.direccion.isNullOrBlank()

            if (camposCompletos) {
                _uiState.update {
                    it.copy(
                        terceraFase = true,
                        segundaFase = false,
                        primeraFase = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        terceraFase = false,
                        segundaFase = true,
                        formCompletado = false
                    )
                }
            }
            return
        }

        // -------- FASE 3: CORREO Y CONTRASEÑAS --------
        if (state.terceraFase) {
            val correo = state.correoElectronico?.trim() ?: ""
            val contrasena = state.contrasena ?: ""
            val repetirContrasena = state.repetirContrasena ?: ""

            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
            val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,}$")

            when {
                correo.isBlank() || contrasena.isBlank() || repetirContrasena.isBlank() -> {
                    Notificacion(
                        "Campos Vacíos",
                        "Por favor completa todos los campos antes de continuar.",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, terceraFase = true) }
                }

                !emailRegex.matches(correo) -> {
                    Notificacion(
                        "Correo Electronico Inválido",
                        "Por favor introduce un correo electrónico válido (ej: ejemplo@correo.com).",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, terceraFase = true) }
                }

                contrasena != repetirContrasena -> {
                    Notificacion(
                        "Advertencia",
                        "Las contraseñas no coinciden.",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, terceraFase = true) }
                }

                !passwordRegex.matches(contrasena) -> {
                    Notificacion(
                        "Contraseña Insegura",
                        "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, terceraFase = true) }
                }

                else -> {
                    _uiState.update {
                        it.copy(
                            formCompletado = true,
                            terceraFase = false
                        )
                    }
                }
            }

            return
        }

    }




    fun consultarCedula(cedula: String) {
        viewModelScope.launch {

            getCiudadanoRepository.getCiudadano(APIKEY, cedula).collect { resourceGetCiudadano ->
                when (resourceGetCiudadano) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(cargando = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {
                        val ciudadanoData = resourceGetCiudadano.data

                        if (ciudadanoData != null) {
                            val nombres = ciudadanoData.Nombres
                            val apellidos = ciudadanoData.Apellidos
                            val fechaNacimiento = ciudadanoData.FechaNacimiento
                            val sexo = ciudadanoData.Sexo
                            val calle = ciudadanoData.Calle
                            val ciudad = ciudadanoData.Ciudad
                            val municipio = ciudadanoData.Municipio
                            val sector = ciudadanoData.Sector
                            val foto = ciudadanoData.Foto

                            val direccionCompleta = listOf(calle, ciudad, municipio, sector)
                                .filterNot { it.isNullOrBlank() }
                                .joinToString(", ")

                            _uiState.update {
                                it.copy(
                                    getCiudadanoDto = ciudadanoData,
                                    cargando = false,
                                    nombres = nombres,
                                    apellidos = apellidos,
                                    fechaNacimiento = fechaNacimiento,
                                    sexo = sexo,
                                    direccion = direccionCompleta,
                                    foto = foto,
                                    segundaFase = true,
                                    primeraFase = false
                                )
                            }
                        } else {
                            _uiState.update {it.copy(cargando = false, segundaFase = true, primeraFase = false, errorMessage = "No se encontraron datos para esta cédula")}

                            Notificacion(
                                "Informacion",
                                "No se encontraron datos para esta cédula",
                                NotificationType.Informacion
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {it.copy(cargando = false, segundaFase = true, primeraFase = false, errorMessage = "Error: ${resourceGetCiudadano.message}")}

                        Notificacion(
                            "Error",
                            "Error: ${resourceGetCiudadano.message}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    private fun generarApiKey(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..24)
            .map { chars.random() }
            .joinToString("")
    }

    fun registrarCuenta(onNavFinalizarRegistroClick: () -> Unit) {
        viewModelScope.launch {
            val nombres = uiState.value.nombres.toString()
            val apellidos = uiState.value.apellidos.toString()
            val fechaNacimiento = uiState.value.fechaNacimiento.toString()
            val sexo = uiState.value.sexo.toString()
            val direccion = uiState.value.direccion.toString()
            val foto = uiState.value.foto.toString()
            val correoElectronico = uiState.value.correoElectronico.toString()
            val contrasena = uiState.value.contrasena.toString()
            val cedula = uiState.value.cedula.toString()

            usuariosRepository.addUsuario(
                APIKEY,
                "agregar_usuarios",
                "Add",
                "2",
                correoElectronico,
                contrasena,
                nombres,
                apellidos,
                direccion,
                sexo,
                foto,
                cedula
            ).collect { resourceUsuario ->
                when (resourceUsuario) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(cargando = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {
                        val validacion = resourceUsuario.data!!.Validacion

                        if (validacion == "Exitoso") {
                            val usuarioId = resourceUsuario.data.usuarioId

                            if (usuarioId != 0) {

                                var intentos = 0
                                var exito = false

                                while (!exito && intentos < 10) {
                                    val nuevaApiKey = generarApiKey()

                                    usuariosRepository.updateApiKey(
                                        APIKEY,
                                        "editar_apikey",
                                        "updateApiKey",
                                        usuarioId.toString(),
                                        nuevaApiKey
                                    ).collect { resourceUsuarioApiKey ->
                                        when (resourceUsuarioApiKey) {
                                            is Resource.Loading -> {
                                                _uiState.update {
                                                    it.copy(cargando = true, errorMessage = null)
                                                }
                                            }

                                            is Resource.Success -> {
                                                val validacionApiKey = resourceUsuarioApiKey.data!!.Validacion.toString()

                                                when (validacionApiKey) {
                                                    "Exitoso" -> {
                                                        exito = true
                                                        Notificacion(
                                                            "Exitoso",
                                                            "Cuenta creada correctamente",
                                                            NotificationType.Exitoso
                                                        )
                                                        onNavFinalizarRegistroClick()
                                                    }

                                                    "Existe" -> {
                                                        intentos++
                                                    }

                                                    else -> {
                                                        Notificacion(
                                                            "Error",
                                                            resourceUsuarioApiKey.data.mensaje.toString(),
                                                            NotificationType.Error
                                                        )
                                                        exito = true
                                                        _uiState.update { it.copy(cargando = false) }
                                                    }
                                                }
                                            }

                                            is Resource.Error -> {
                                                Notificacion(
                                                    "Error",
                                                    "Error al actualizar la API Key",
                                                    NotificationType.Error
                                                )
                                                exito = true
                                                _uiState.update { it.copy(cargando = false) }
                                            }
                                        }
                                    }
                                }

                                if (!exito) {
                                    Notificacion(
                                        "Error",
                                        "No se pudo generar una API Key única tras varios intentos",
                                        NotificationType.Error
                                    )
                                    _uiState.update { it.copy(cargando = false) }
                                }
                            } else {
                                Notificacion(
                                    "Error",
                                    "Se ha detectado un error al identificar al nuevo usuario.",
                                    NotificationType.Error
                                )
                                _uiState.update { it.copy(cargando = false) }
                            }
                        } else {
                            Notificacion(
                                "Información",
                                resourceUsuario.data.mensaje.toString(),
                                NotificationType.Informacion
                            )
                            _uiState.update { it.copy(cargando = false) }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                cargando = false,
                                segundaFase = true,
                                errorMessage = "Error: ${resourceUsuario.data!!.mensaje}"
                            )
                        }

                        Notificacion(
                            "Error",
                            "Error: ${resourceUsuario.data!!.mensaje}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }



    fun onCedulaChange(cedula: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        val nuevaCedula = cedula ?: ""
        if (nuevaCedula.length < 11) {
            _uiState.update { it.copy(cedula = nuevaCedula) }
        }
        else if(nuevaCedula.length == 11){
            _uiState.update {it.copy(cedula = nuevaCedula, ocultarTeclado = true)}
        }
    }

    fun onNombresChange(nombres: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(nombres = nombres.toString()) }
    }

    fun onApellidosChange(apellidos: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(apellidos = apellidos.toString()) }
    }

    fun onSexoChange(sexo: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(sexo = sexo.toString()) }
    }

    fun onFechaNacimientoChange(fechaNacimiento: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(fechaNacimiento = fechaNacimiento.toString()) }
    }

    fun onTelefonoChange(telefono: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        val nuevaTelefono = telefono ?: ""
        if (nuevaTelefono.length < 10) {
            _uiState.update { it.copy(telefono = nuevaTelefono) }
        }
        else if(nuevaTelefono.length >= 10){
            _uiState.update {it.copy(telefono = nuevaTelefono, ocultarTeclado = true)}
        }
    }

    fun onDireccionChange(direccion: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(direccion = direccion.toString()) }
    }

    fun onCorreoElectronicoChange(correoElectronico: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(correoElectronico = correoElectronico.toString()) }
    }

    fun onContrasenaChange(contrasena: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(contrasena = contrasena.toString()) }
    }

    fun onRepetirContrasenaChange(repetirContrasena: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(repetirContrasena = repetirContrasena.toString()) }
    }

    fun onPrimeraFaseClick() = viewModelScope.launch {
        _uiState.update {it.copy(ocultarTeclado = true)}
        validarCampos()
    }

    fun onSegundaFaseClick() = viewModelScope.launch {
        _uiState.update {it.copy(ocultarTeclado = true)}
        validarCampos()
    }

    fun onTerceraFaseClick() = viewModelScope.launch {
        _uiState.update {it.copy(ocultarTeclado = true)}
        validarCampos()
    }
    fun onFinalizarRegistroClick(onNavFinalizarRegistroClick: () -> Unit) = viewModelScope.launch {
        _uiState.update {it.copy(ocultarTeclado = true)}
        registrarCuenta(onNavFinalizarRegistroClick)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val getCiudadanoDto: GetCiudadanoDto? = null,
    val cargando: Boolean = false,
    val errorMessage: String? = null,
    val cedula: String? = null,
    val foto: String? = null,
    val nombres: String? = null,
    val apellidos: String? = null,
    val sexo: String? = null,
    val fechaNacimiento: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val correoElectronico: String? = null,
    val contrasena: String? = null,
    val repetirContrasena: String? = null,
    val primeraFase: Boolean = true,
    val segundaFase: Boolean = false,
    val terceraFase: Boolean = false,
    val formCompletado: Boolean = false,
    val ocultarTeclado: Boolean = false
)
