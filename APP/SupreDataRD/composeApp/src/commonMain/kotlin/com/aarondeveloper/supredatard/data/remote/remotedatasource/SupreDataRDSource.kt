package com.aarondeveloper.supredatard.data.remote.remotedatasource

import com.aarondeveloper.supredatard.data.remote.api.SupreDataRDApi

class SupreDataRDSource(private val api: SupreDataRDApi) {

    // === Ciudadano, Vehículo, Licencia, Contribuyente ===
    suspend fun getCiudadano(apiKey : String, nCedula : String) = api.getCiudadano(apiKey = apiKey, nCedula = nCedula)
    suspend fun getVehiculo(apiKey : String, nPlaca : String) = api.getVehiculo(apiKey = apiKey, nPlaca = nPlaca)
    suspend fun getLicencia(apiKey : String, nCedula : String) = api.getLicencia(apiKey = apiKey, nCedula = nCedula)
    suspend fun getContribuyente(apiKey : String, nRNC : String) = api.getContribuyente(apiKey = apiKey, nRNC = nRNC)

    suspend fun getFoto(apiKey : String, nCedula : String) = api.getFoto(apiKey = apiKey, nCedula = nCedula)

    // === Usuarios ===
    suspend fun addUsuario(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, correo: String, contrasena: String,
        nombres: String, apellidos: String, direccion: String,
        sexo: String, foto: String, cedula: String
    ) = api.addUsuario(apiKey, permiso, metodo, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula)

    suspend fun updateUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, rolId: String, correo: String,
        contrasena: String, nombres: String, apellidos: String,
        direccion: String, sexo: String, foto: String, cedula: String
    ) = api.updateUsuario(apiKey, permiso, metodo, usuarioId, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula)

    suspend fun updatePass(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, contrasena: String
    ) = api.updatePass(apiKey, permiso, metodo, usuarioId, contrasena)

    suspend fun updateToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ) = api.updateToken(apiKey, permiso, metodo, usuarioId)

    suspend fun verificarToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, token: String
    ) = api.verificarToken(apiKey, permiso, metodo, usuarioId, token)

    suspend fun iniciarSesion(
        apiKey: String, permiso: String, metodo: String,
        correo: String, contrasena: String
    ) = api.iniciarSesion(apiKey, permiso, metodo, correo, contrasena)

    suspend fun cerrarSesion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ) = api.cerrarSesion(apiKey, permiso, metodo, usuarioId)

    suspend fun deleteUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ) = api.deleteUsuario(apiKey, permiso, metodo, usuarioId)

    suspend fun getUsuarioById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ) = api.getUsuarioById(apiKey, permiso, metodo, usuarioId)

    suspend fun getUsuarioNoFotoById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ) = api.getUsuarioNoFotoById(apiKey, permiso, metodo, usuarioId)

    suspend fun getAllUsuarios(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllUsuarios(apiKey, permiso, metodo)

    suspend fun getAllNoFotoUsuarios(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllNoFotoUsuarios(apiKey, permiso, metodo)

    suspend fun updateApiKey(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, newApiKey: String
    ) = api.updateApiKey(apiKey, permiso, metodo, usuarioId, newApiKey)

    suspend fun checkApiKey(
        apiKey: String, permiso: String, metodo: String,
        checkKey: String
    ) = api.checkApiKey(apiKey, permiso, metodo, checkKey)


    // === Roles ===
    suspend fun addRol(
        apiKey: String, permiso: String, metodo: String,
        descripcion: String
    ) = api.addRol(apiKey, permiso, metodo, descripcion)

    suspend fun updateRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, descripcion: String
    ) = api.updateRol(apiKey, permiso, metodo, rolId, descripcion)

    suspend fun deleteRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ) = api.deleteRol(apiKey, permiso, metodo, rolId)

    suspend fun getRolById(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ) = api.getRolById(apiKey, permiso, metodo, rolId)

    suspend fun getAllRoles(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllRoles(apiKey, permiso, metodo)


    // === Suscripciones ===
    suspend fun addSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, usuarioId: String, pagoId: String,
        descripcion: String, fechaInicio: String, fechaFin: String
    ) = api.addSuscripcion(apiKey, permiso, metodo, herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin)

    suspend fun updateSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String, herramientaId: String, usuarioId: String,
        pagoId: String, descripcion: String, fechaInicio: String, fechaFin: String
    ) = api.updateSuscripcion(apiKey, permiso, metodo, suscripcionId, herramientaId, usuarioId, pagoId, descripcion, fechaInicio, fechaFin)

    suspend fun deleteSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ) = api.deleteSuscripcion(apiKey, permiso, metodo, suscripcionId)

    suspend fun getSuscripcionById(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ) = api.getSuscripcionById(apiKey, permiso, metodo, suscripcionId)

    suspend fun getAllSuscripciones(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllSuscripciones(apiKey, permiso, metodo)

    suspend fun checkSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, herramientaId: String
    ) = api.checkSuscripcion(apiKey, permiso, metodo, usuarioId, herramientaId)


    // === Pagos ===
    suspend fun addPago(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, descripcion: String, metodoPago: String,
        monto: String, fecha: String
    ) = api.addPago(apiKey, permiso, metodo, usuarioId, descripcion, metodoPago, monto, fecha)

    suspend fun updatePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String, usuarioId: String, descripcion: String,
        metodoPago: String, monto: String, fecha: String
    ) = api.updatePago(apiKey, permiso, metodo, pagoId, usuarioId, descripcion, metodoPago, monto, fecha)

    suspend fun deletePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ) = api.deletePago(apiKey, permiso, metodo, pagoId)

    suspend fun getPagoById(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ) = api.getPagoById(apiKey, permiso, metodo, pagoId)

    suspend fun getAllPagos(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllPagos(apiKey, permiso, metodo)


    // === Permisos ===
    suspend fun addPermiso(
        apiKey: String, permiso: String, metodo: String,
        nombre: String, descripcion: String
    ) = api.addPermiso(apiKey, permiso, metodo, nombre, descripcion)

    suspend fun updatePermiso(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String, nombre: String, descripcion: String
    ) = api.updatePermiso(apiKey, permiso, metodo, permisoId, nombre, descripcion)

    suspend fun deletePermiso(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String
    ) = api.deletePermiso(apiKey, permiso, metodo, permisoId)

    suspend fun getPermisoById(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String
    ) = api.getPermisoById(apiKey, permiso, metodo, permisoId)

    suspend fun getAllPermisos(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllPermisos(apiKey, permiso, metodo)


    // === Accesos ===
    suspend fun addAcceso(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, permisoId: String
    ) = api.addAcceso(apiKey, permiso, metodo, rolId, permisoId)

    suspend fun updateAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String, rolId: String, permisoId: String
    ) = api.updateAcceso(apiKey, permiso, metodo, accesoId, rolId, permisoId)

    suspend fun deleteAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ) = api.deleteAcceso(apiKey, permiso, metodo, accesoId)

    suspend fun getAccesoById(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ) = api.getAccesoById(apiKey, permiso, metodo, accesoId)

    suspend fun getAllAccesos(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllAccesos(apiKey, permiso, metodo)


    // === Herramientas ===
    suspend fun addHerramienta(
        apiKey: String, permiso: String, metodo: String,
        icono: String, nombre: String, creador: String, estado: String
    ) = api.addHerramienta(apiKey, permiso, metodo, icono, nombre, creador, estado)

    suspend fun updateHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, icono: String, nombre: String, creador: String, estado: String
    ) = api.updateHerramienta(apiKey, permiso, metodo, herramientaId, icono, nombre, creador, estado)

    suspend fun deleteHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ) = api.deleteHerramienta(apiKey, permiso, metodo, herramientaId)

    suspend fun getHerramientaById(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ) = api.getHerramientaById(apiKey, permiso, metodo, herramientaId)

    suspend fun getAllHerramientas(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllHerramientas(apiKey, permiso, metodo)


    // === Codigos ===

    suspend fun addCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String, descripcion: String, expiracion: String, estado: String
    ) = api.addCodigo(apiKey, permiso, metodo, codigo, descripcion, expiracion, estado)


    suspend fun updateCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String, codigo: String, descripcion: String, expiracion: String, estado: String
    ) = api.updateCodigo(apiKey, permiso, metodo, codigoId, codigo, descripcion, expiracion, estado)


    suspend fun deleteCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ) = api.deleteCodigo(apiKey, permiso, metodo, codigoId)


    suspend fun getCodigoById(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ) = api.getCodigoById(apiKey, permiso, metodo, codigoId)


    suspend fun getAllCodigos(
        apiKey: String, permiso: String, metodo: String
    ) = api.getAllCodigos(apiKey, permiso, metodo)


    suspend fun verificarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ) = api.verificarCodigo(apiKey, permiso, metodo, codigo)


    suspend fun usarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ) = api.usarCodigo(apiKey, permiso, metodo, codigo)

    suspend fun sendCorreo(
        apiKey: String,
        permiso: String,
        metodo: String,
        smtpHost: String,
        smtpUsername: String,
        smtpPassword: String,
        smtpPort: String,
        asunto: String,
        mensaje: String,
        piePagina: String,
        destinatario: String,
        nombreEmpresa: String,
        direccionEmpresa: String
    ) = api.sendCorreo(
        apiKey = apiKey,
        permiso = permiso,
        metodo = metodo,
        smtpHost = smtpHost,
        smtpUsername = smtpUsername,
        smtpPassword = smtpPassword,
        smtpPort = smtpPort,
        asunto = asunto,
        mensaje = mensaje,
        piePagina = piePagina,
        destinatario = destinatario,
        nombreEmpresa = nombreEmpresa,
        direccionEmpresa = direccionEmpresa
    )


}