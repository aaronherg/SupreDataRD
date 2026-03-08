package com.aarondeveloper.supredatard.data.repository

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.librery.Resource
import com.aarondeveloper.supredatard.data.remote.remotedatasource.SupreDataRDSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsuariosRepository(
    private val supreDataRDSource: SupreDataRDSource
) {

    fun addUsuario(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, correo: String, contrasena: String,
        nombres: String, apellidos: String, direccion: String,
        sexo: String, foto: String, cedula: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.addUsuario(apiKey, permiso, metodo, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, rolId: String, correo: String,
        contrasena: String, nombres: String, apellidos: String,
        direccion: String, sexo: String, foto: String, cedula: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.updateUsuario(apiKey, permiso, metodo, usuarioId, rolId, correo, contrasena, nombres, apellidos, direccion, sexo, foto, cedula)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updatePass(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, contrasena: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.updatePass(apiKey, permiso, metodo, usuarioId, contrasena)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.updateToken(apiKey, permiso, metodo, usuarioId)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun verificarToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, token: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.verificarToken(apiKey, permiso, metodo, usuarioId, token)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun iniciarSesion(
        apiKey: String, permiso: String, metodo: String,
        correo: String, contrasena: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.iniciarSesion(apiKey, permiso, metodo, correo, contrasena)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun cerrarSesion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.cerrarSesion(apiKey, permiso, metodo, usuarioId)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun deleteUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.deleteUsuario(apiKey, permiso, metodo, usuarioId)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getUsuarioById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.getUsuarioById(apiKey, permiso, metodo, usuarioId)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun getUsuarioNoFotoById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.getUsuarioNoFotoById(apiKey, permiso, metodo, usuarioId)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
    fun getAllUsuarios(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<UsuariosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val usuarios = supreDataRDSource.getAllUsuarios(apiKey, permiso, metodo)
            emit(Resource.Success(usuarios))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
    fun getAllNoFotoUsuarios(
        apiKey: String, permiso: String, metodo: String
    ): Flow<Resource<List<UsuariosDto>>> = flow {
        try {
            emit(Resource.Loading())
            val usuarios = supreDataRDSource.getAllNoFotoUsuarios(apiKey, permiso, metodo)
            emit(Resource.Success(usuarios))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun updateApiKey(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, newApiKey: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.updateApiKey(apiKey, permiso, metodo, usuarioId, newApiKey)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }

    fun checkApiKey(
        apiKey: String, permiso: String, metodo: String,
        checkKey: String
    ): Flow<Resource<UsuariosDto>> = flow {
        try {
            emit(Resource.Loading())
            val usuario = supreDataRDSource.checkApiKey(apiKey, permiso, metodo, checkKey)
            emit(Resource.Success(usuario))
        } catch (e: Exception) {
            emit(Resource.Error("Conexión fallida: ${e.message}"))
        }
    }
}
