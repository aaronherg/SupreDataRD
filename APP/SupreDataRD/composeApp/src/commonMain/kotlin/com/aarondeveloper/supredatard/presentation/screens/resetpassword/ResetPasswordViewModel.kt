package com.aarondeveloper.supredatard.presentation.screens.resetpassword

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.data.repository.CodigosRepository
import com.aarondeveloper.supredatard.data.repository.CorreosRepository
import com.aarondeveloper.supredatard.data.repository.UsuariosRepository
import com.aarondeveloper.supredatard.librery.APIKEY
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.librery.SMTPHOST
import com.aarondeveloper.supredatard.librery.SMTPPASSWORD
import com.aarondeveloper.supredatard.librery.SMTPPORT
import com.aarondeveloper.supredatard.librery.SMTPUSERNAME
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class ResetPasswordViewModel(
    private val usuariosRepository: UsuariosRepository,
    private val codigosRepository: CodigosRepository,
    private val correosRepository: CorreosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun generarCodigoNumerico(length: Int = 6): String {
        return (1..length)
            .map { Random.nextInt(0, 10) }
            .joinToString("")
    }


    fun generarExpiracion(minutesToAdd: Long = 30): String {
        val now: Instant = Clock.System.now()

        val duration = minutesToAdd.toDuration(DurationUnit.MINUTES)
        val newInstant = now + duration
        val local = newInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        val year = local.year.toString().padStart(4, '0')
        val month = local.monthNumber.toString().padStart(2, '0')
        val day = local.dayOfMonth.toString().padStart(2, '0')
        val hour = local.hour.toString().padStart(2, '0')
        val minute = local.minute.toString().padStart(2, '0')
        val second = local.second.toString().padStart(2, '0')

        return "$year-$month-$day $hour:$minute:$second"
    }

    fun validarCampos(onNavFinalizarResetPasswordClick: (() -> Unit)? = null) {
        val state = _uiState.value

        // -------- FASE 1: CORREO ELECTRONICO --------
        if (state.primeraFase) {
            val correo = state.correoElectronico?.trim() ?: ""
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

            when {
                correo.isBlank() -> {
                    Notificacion(
                        "Correo Vacío",
                        "Por favor introduce tu correo electrónico.",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, primeraFase = true) }
                }
                !emailRegex.matches(correo) -> {
                    Notificacion(
                        "Correo Inválido",
                        "Introduce un correo válido (ej: ejemplo@correo.com).",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, primeraFase = true) }
                }
                else -> {

                    verificarCorreoElectronico()
                }
            }
            return
        }

        // -------- FASE 2: CODIGO DE VERIFICACION --------
        if (state.segundaFase) {
            val codigos = listOf(
                state.primerCodigo,
                state.segundoCodigo,
                state.tercerCodigo,
                state.cuartoCodigo,
                state.quintoCodigo,
                state.sextoCodigo
            )

            if (codigos.any { it.isNullOrBlank() || it.length != 1 }) {
                Notificacion(
                    "Código Incompleto",
                    "Por favor completa los 6 dígitos del código.",
                    NotificationType.Advertencia
                )
                _uiState.update { it.copy(formCompletado = false, segundaFase = true) }
            } else {

                verificarCodigo()
            }
            return
        }

        // -------- FASE 3: CONTRASEÑA --------
        if (state.terceraFase) {
            val contrasena = state.contrasena ?: ""
            val repetirContrasena = state.repetirContrasena ?: ""
            val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,}$")

            when {
                contrasena.isBlank() || repetirContrasena.isBlank() -> {
                    Notificacion(
                        "Campos Vacíos",
                        "Por favor completa la contraseña y su repetición.",
                        NotificationType.Advertencia
                    )
                    _uiState.update { it.copy(formCompletado = false, terceraFase = true) }
                }
                contrasena != repetirContrasena -> {
                    Notificacion(
                        "Contraseñas no coinciden",
                        "Verifica que ambas contraseñas sean iguales.",
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
                            terceraFase = false,
                            ocultarTeclado = true
                        )
                    }

                    if(onNavFinalizarResetPasswordClick != null){
                        resetPasssword(onNavFinalizarResetPasswordClick)
                    }
                    else{
                        Notificacion(
                            "Error",
                            "Se ha detectado un error al tratar de cambiar su contraseña.",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun resetPasssword(onNavFinalizarResetPasswordClick: () -> Unit){


        viewModelScope.launch {

            val state = _uiState.value

            if (state.formCompletado) {

                val usuarioId = _uiState.value.usuarioId
                val contrasena = _uiState.value.contrasena

                if (usuarioId != 0) {

                    usuariosRepository.updatePass(
                        APIKEY,
                        "editar_contrasena",
                        "UpdatePass",
                        usuarioId.toString(),
                        contrasena.toString()
                    ).collect { resourceUsuario ->
                        when (resourceUsuario) {
                            is Resource.Loading -> {
                                _uiState.update {
                                    it.copy(cargando = true, errorMessage = null)
                                }
                            }

                            is Resource.Success -> {

                                val validacion = resourceUsuario.data!!.Validacion.toString()

                                if(validacion == "Exitoso"){
                                    Notificacion(
                                        "Contraseña Restablecida",
                                        "Se ha restablecido correctamente su contraseña.",
                                        NotificationType.Exitoso
                                    )

                                    onNavFinalizarResetPasswordClick()
                                }
                                else{

                                    Notificacion(
                                        "Error",
                                        resourceUsuario.data!!.mensaje.toString(),
                                        NotificationType.Error
                                    )

                                    _uiState.update {
                                        it.copy(cargando = false, errorMessage = null)
                                    }
                                }
                            }

                            is Resource.Error -> {
                                _uiState.update {
                                    it.copy(
                                        cargando = false,
                                        errorMessage = "Error: ${resourceUsuario.message}"
                                    )
                                }

                                Notificacion(
                                    "Error",
                                    "Error: ${resourceUsuario.message}",
                                    NotificationType.Error
                                )
                            }
                        }
                    }

                } else {
                    Notificacion(
                        "Error",
                        "Error: Se ha producido un error al tratar de restablecer su contraseña.",
                        NotificationType.Error
                    )
                }
            }
            else{
                Notificacion(
                    "Error",
                    "Se ha detectado un error al tratar de cambiar su contraseña.",
                    NotificationType.Error
                )
            }

        }
    }

    fun verificarCorreoElectronico(){

        viewModelScope.launch {

            usuariosRepository.getAllNoFotoUsuarios(APIKEY, "listar_usuarios", "getAllNoFoto").collect { resourceUsuario ->
                when (resourceUsuario) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(cargando = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {
                        val listaUsuario = resourceUsuario.data ?: emptyList()
                        val correo = _uiState.value.correoElectronico?.trim()?.lowercase().toString()

                        val usuarioExiste = listaUsuario.find {
                            it.correo?.trim()?.lowercase() == correo
                        }

                        if (usuarioExiste != null) {

                            _uiState.update {
                                it.copy(usuarioId = usuarioExiste.usuarioId)
                            }
                            enviarCodigoVerificacion(correo)

                        }
                        else {
                            Notificacion(
                                "Correo no registrado",
                                "El correo que has introducido no está registrado en el sistema.",
                                NotificationType.Informacion
                            )

                            _uiState.update {
                                it.copy(
                                    cargando = false,
                                    errorMessage = null
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                cargando = false,
                                errorMessage = "Error: ${resourceUsuario.message}"
                            )
                        }

                        Notificacion(
                            "Error",
                            "Error: ${resourceUsuario.message}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun enviarCodigoVerificacion(correo: String) {
        viewModelScope.launch {

            val descripcion = "Codigo de Verificacion - Restablecer Contraseña del Usuario"
            val expiracion = generarExpiracion(30)
            val estado = "Disponible"

            codigosRepository.getAllCodigos(APIKEY, "listar_codigos", "getAll").collect { resourceCodigoLista ->
                when (resourceCodigoLista) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(cargando = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {

                        val listaCodigos = resourceCodigoLista.data?.map { it.codigo } ?: emptyList()

                        var codigo: String
                        do {
                            codigo = generarCodigoNumerico()
                        } while (listaCodigos.contains(codigo))

                        codigosRepository.addCodigo(APIKEY, "agregar_codigo", "add", codigo, descripcion, expiracion, estado).collect { resourceCodigo ->
                            when (resourceCodigo) {
                                is Resource.Loading -> {
                                    _uiState.update {
                                        it.copy(cargando = true, errorMessage = null)
                                    }
                                }

                                is Resource.Success -> {

                                    val validacion = resourceCodigo.data!!.Validacion

                                    if(validacion == "Exitoso"){

                                        val smtpHost = SMTPHOST
                                        val smtpUsername = SMTPUSERNAME
                                        val smtpPassword = SMTPPASSWORD
                                        val smtpPort = SMTPPORT
                                        val asunto = "CENTRO DE EXPEDIENTES"
                                        val mensaje = "Gracias por ser parte de nosotros. Para completar el proceso de verificación, utilice el siguiente código: **${codigo}**. Este código es válido por un corto tiempo. Introdúzcalo en el sistema lo antes posible para completar la verificación. Si no solicitó este código, ignore este mensaje."
                                        val piePagina = "¡Gracias por ser parte de nosotros!"
                                        val destinatario = correo
                                        val nombreEmpresa = "SupreDataRD"
                                        val direccionEmpresa = "Virtual"

                                        correosRepository.sendCorreo(APIKEY, "enviar_correo", "enviarCorreo", smtpHost, smtpUsername, smtpPassword, smtpPort, asunto, mensaje, piePagina, destinatario, nombreEmpresa, direccionEmpresa).collect { resourceCorreo ->
                                            when (resourceCorreo) {
                                                is Resource.Loading -> {
                                                    _uiState.update {
                                                        it.copy(cargando = true, errorMessage = null)
                                                    }
                                                }

                                                is Resource.Success -> {

                                                    val validacionCorreo = resourceCorreo.data!!.Validacion

                                                    _uiState.update {
                                                        it.copy(cargando = false, errorMessage = null)
                                                    }

                                                    if (validacionCorreo == "Exitoso") {

                                                        _uiState.update {
                                                            it.copy(
                                                                primeraFase = false,
                                                                segundaFase = true,
                                                                cargando = false,
                                                                formCompletado = false,
                                                                ocultarTeclado = true,
                                                                errorMessage = null
                                                            )
                                                        }

                                                        Notificacion(
                                                            "Codigo Enviado",
                                                            "Verifique su bandera de entrada de su correo electronico.",
                                                            NotificationType.Exitoso
                                                        )
                                                    } else {
                                                        Notificacion(
                                                            "Error",
                                                            "Error: ${resourceCorreo.data!!.mensaje}",
                                                            NotificationType.Error
                                                        )
                                                        _uiState.update {
                                                            it.copy(cargando = false, errorMessage = resourceCorreo.data!!.mensaje)
                                                        }
                                                    }
                                                }

                                                is Resource.Error -> {
                                                    _uiState.update {
                                                        it.copy(
                                                            cargando = false,
                                                            errorMessage = "Error: ${resourceCorreo.data!!.mensaje}"
                                                        )
                                                    }

                                                    Notificacion(
                                                        "Error",
                                                        "Error: ${resourceCorreo.data!!.mensaje}",
                                                        NotificationType.Error
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        Notificacion(
                                            "Error",
                                            "Error: ${resourceCodigo.data!!.mensaje}",
                                            NotificationType.Error
                                        )
                                        _uiState.update {
                                            it.copy(cargando = false, errorMessage = resourceCodigo.data!!.mensaje)
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _uiState.update {
                                        it.copy(
                                            cargando = false,
                                            errorMessage = "Error: ${resourceCodigo.data!!.mensaje}"
                                        )
                                    }

                                    Notificacion(
                                        "Error",
                                        "Error: ${resourceCodigo.data!!.mensaje}",
                                        NotificationType.Error
                                    )
                                }
                            }
                        }

                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                cargando = false,
                                errorMessage = "Error: ${resourceCodigoLista.message}"
                            )
                        }

                        Notificacion(
                            "Error",
                            "Error: ${resourceCodigoLista.message}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }

    fun verificarCodigo(){

        viewModelScope.launch {

            val codigo = listOfNotNull(
                _uiState.value.primerCodigo,
                _uiState.value.segundoCodigo,
                _uiState.value.tercerCodigo,
                _uiState.value.cuartoCodigo,
                _uiState.value.quintoCodigo,
                _uiState.value.sextoCodigo
            ).joinToString(separator = "").trim()

            codigosRepository.verificarCodigo(APIKEY, "verificar_codigo", "verificar", codigo).collect { resourceCodigo ->
                when (resourceCodigo) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(cargando = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {

                        val validacion = resourceCodigo.data!!.Validacion
                        val estado = resourceCodigo.data!!.estado

                        if (validacion == "Exitoso" && estado == "Disponible") {

                            codigosRepository.usarCodigo(APIKEY, "usar_codigo", "usarCodigo", codigo).collect { resourceCodigoUsar ->
                                when (resourceCodigoUsar) {
                                    is Resource.Loading -> {
                                        _uiState.update {
                                            it.copy(cargando = true, errorMessage = null)
                                        }
                                    }

                                    is Resource.Success -> {

                                        val validacionUsarCodigo = resourceCodigoUsar.data!!.Validacion

                                        if (validacionUsarCodigo == "Exitoso") {

                                            _uiState.update {
                                                it.copy(
                                                    cargando = false,
                                                    segundaFase = false,
                                                    terceraFase = true,
                                                    ocultarTeclado = true
                                                )
                                            }

                                        }
                                        else{
                                            Notificacion(
                                                "Advertencia",
                                                "${resourceCodigoUsar.data!!.mensaje}",
                                                NotificationType.Advertencia
                                            )

                                            _uiState.update {
                                                it.copy(
                                                    cargando = false,
                                                    errorMessage = "Error: ${resourceCodigoUsar.data!!.mensaje}"
                                                )
                                            }
                                        }

                                    }

                                    is Resource.Error -> {
                                        _uiState.update {
                                            it.copy(
                                                cargando = false,
                                                errorMessage = "Error: ${resourceCodigoUsar.data!!.mensaje}"
                                            )
                                        }

                                        Notificacion(
                                            "Error",
                                            "Error: ${resourceCodigoUsar.data!!.mensaje}",
                                            NotificationType.Advertencia
                                        )
                                    }
                                }
                            }

                        }
                        else{
                            Notificacion(
                                "Advertencia",
                                "${resourceCodigo.data!!.mensaje}",
                                NotificationType.Advertencia
                            )
                            _uiState.update {
                                it.copy(
                                    cargando = false,
                                    errorMessage = "Error: ${resourceCodigo.data!!.mensaje}"
                                )
                            }
                        }

                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                cargando = false,
                                errorMessage = "Error: ${resourceCodigo.data!!.mensaje}"
                            )
                        }

                        Notificacion(
                            "Error",
                            "Error: ${resourceCodigo.data!!.mensaje}",
                            NotificationType.Error
                        )
                    }
                }
            }
        }
    }


    fun onCorreoElectronicoChange(correoElectronico: String?) {
        _uiState.update {it.copy(ocultarTeclado = false)}
        _uiState.update { it.copy(correoElectronico = correoElectronico.toString()) }
    }

    fun onPrimerCodigoChange(primerCodigo: String) {
        viewModelScope.launch {
            val valor = primerCodigo.take(1)
            _uiState.update { it.copy(primerCodigo = valor) }
        }
    }

    fun onSegundoCodigoChange(segundoCodigo: String) {
        viewModelScope.launch {
            val valor = segundoCodigo.take(1)
            _uiState.update { it.copy(segundoCodigo = valor) }
        }
    }

    fun onTercerCodigoChange(tercerCodigo: String) {
        viewModelScope.launch {
            val valor = tercerCodigo.take(1)
            _uiState.update { it.copy(tercerCodigo = valor) }
        }
    }

    fun onCuartoCodigoChange(cuartoCodigo: String) {
        viewModelScope.launch {
            val valor = cuartoCodigo.take(1)
            _uiState.update { it.copy(cuartoCodigo = valor) }
        }
    }

    fun onQuintoCodigoChange(quintoCodigo: String) {
        viewModelScope.launch {
            val valor = quintoCodigo.take(1)
            _uiState.update { it.copy(quintoCodigo = valor) }
        }
    }

    fun onSextoCodigoChange(sextoCodigo: String) {
        viewModelScope.launch {
            val valor = sextoCodigo.take(1)
            _uiState.update { it.copy(sextoCodigo = valor, ocultarTeclado = true) }
        }
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
    fun onFinalizarResetPasswordClick(onNavFinalizarResetPasswordClick: () -> Unit) = viewModelScope.launch {
        _uiState.update {it.copy(ocultarTeclado = true)}
        validarCampos(onNavFinalizarResetPasswordClick)
    }

}

data class UiState(
    val usuarioDto: UsuariosDto? = null,
    val listaUsuarioDto: List<UsuariosDto>? = null,
    val cargando: Boolean = false,
    val usuarioId: Int? = null,
    val errorMessage: String? = null,
    val correoElectronico: String? = null,
    val contrasena: String? = null,
    val repetirContrasena: String? = null,
    val primerCodigo: String? = null,
    val segundoCodigo: String? = null,
    val tercerCodigo: String? = null,
    val cuartoCodigo: String? = null,
    val quintoCodigo: String? = null,
    val sextoCodigo: String? = null,
    val primeraFase: Boolean = true,
    val segundaFase: Boolean = false,
    val terceraFase: Boolean = false,
    val formCompletado: Boolean = false,
    var ocultarTeclado: Boolean = false
)
