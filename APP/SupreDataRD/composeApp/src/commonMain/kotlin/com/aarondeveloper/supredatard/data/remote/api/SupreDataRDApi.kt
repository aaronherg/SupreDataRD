package com.aarondeveloper.supredatard.data.remote.api

import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.AccesosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.CodigosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.CorreosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetCiudadanoDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetContribuyenteDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetFotoDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetLicenciaDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.GetVehiculoDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.HerramientasDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.PagosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.PermisosDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.RolesDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.SuscripcionesDto
import com.aarondeveloper.supredatard.data.remote.dto.supredatardapi.UsuariosDto
import com.aarondeveloper.supredatard.librery.logDebug
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class SupreDataRDApi(private val client: HttpClient, private val baseUrl: String) {

    // GetCiudadano
    suspend fun getCiudadano(apiKey: String, nCedula: String): GetCiudadanoDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/public/getCiudadano/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("nCedula", nCedula)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<GetCiudadanoDto>(respuestaConValidacion)
        } else {
            GetCiudadanoDto(Validacion = validacion)
        }
    }



    // GetVehiculo
    suspend fun getVehiculo(apiKey: String, nPlaca: String): GetVehiculoDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/public/getVehiculo/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("nPlaca", nPlaca)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<GetVehiculoDto>(respuestaConValidacion)
        } else {
            GetVehiculoDto(Validacion = validacion)
        }
    }


    // GetLicencia
    suspend fun getLicencia(apiKey: String, nCedula: String): GetLicenciaDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/public/getLicencia/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("nCedula", nCedula)
            }
        ).body()


        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<GetLicenciaDto>(respuestaConValidacion)
        } else {
            GetLicenciaDto(Validacion = validacion)
        }
    }

    // GetContribuyente
    suspend fun getContribuyente(apiKey: String, nRNC: String): GetContribuyenteDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/public/getContribuyente/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("nRNC", nRNC)
            }
        ).body()


        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<GetContribuyenteDto>(respuestaConValidacion)
        } else {
            GetContribuyenteDto(Validacion = validacion)
        }
    }

    // GetFoto
    suspend fun getFoto(apiKey: String, nCedula: String): GetFotoDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/public/getFoto/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("nCedula", nCedula)
            }
        ).body()


        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<GetFotoDto>(respuestaConValidacion)
        } else {
            GetFotoDto(Validacion = validacion)
        }
    }



    // === Add Usuario ===
    suspend fun addUsuario(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, correo: String, contrasena: String,
        nombres: String, apellidos: String, direccion: String,
        sexo: String, foto: String, cedula: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("rolId", rolId)
                append("correo", correo)
                append("contrasena", contrasena)
                append("nombres", nombres)
                append("apellidos", apellidos)
                append("direccion", direccion)
                append("sexo", sexo)
                append("foto", foto)
                append("cedula", cedula)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Update Usuario ===
    suspend fun updateUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, rolId: String, correo: String,
        contrasena: String, nombres: String, apellidos: String,
        direccion: String, sexo: String, foto: String, cedula: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("usuarioId", usuarioId)
                append("rolId", rolId)
                append("correo", correo)
                append("contrasena", contrasena)
                append("nombres", nombres)
                append("apellidos", apellidos)
                append("direccion", direccion)
                append("sexo", sexo)
                append("foto", foto)
                append("cedula", cedula)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Update Password ===
    suspend fun updatePass(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, contrasena: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("usuarioId", usuarioId)
                append("contrasena", contrasena)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Update Token ===
    suspend fun updateToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Verificar Token ===
    suspend fun verificarToken(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, token: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
                append("token", token)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Iniciar Sesión ===
    suspend fun iniciarSesion(
        apiKey: String, permiso: String, metodo: String,
        correo: String, contrasena: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("correo", correo)
                append("contrasena", contrasena)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Cerrar Sesión ===
    suspend fun cerrarSesion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Delete Usuario ===
    suspend fun deleteUsuario(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Get Usuario By ID ===
    suspend fun getUsuarioById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Get Usuario By ID sin foto ===
    suspend fun getUsuarioNoFotoById(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Get All Usuarios ===
    suspend fun getAllUsuarios(
        apiKey: String, permiso: String, metodo: String
    ): List<UsuariosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val usuariosArray = respuesta?.get("usuarios")?.jsonArray ?: return emptyList()

        return usuariosArray.map { usuario ->
            val usuarioConValidacion = buildJsonObject {
                usuario.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<UsuariosDto>(usuarioConValidacion)
        }
    }

    // === Get All Usuarios sin Foto ===

    suspend fun getAllNoFotoUsuarios(
        apiKey: String, permiso: String, metodo: String
    ): List<UsuariosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val usuariosArray = respuesta?.get("usuarios")?.jsonArray ?: return emptyList()

        return usuariosArray.map { usuario ->
            val usuarioConValidacion = buildJsonObject {
                usuario.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<UsuariosDto>(usuarioConValidacion)
        }
    }


    // === Update API Key ===
    suspend fun updateApiKey(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, newApiKey: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
                append("apikeyNueva", newApiKey)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }

    // === Check API Key ===
    suspend fun checkApiKey(
        apiKey: String, permiso: String, metodo: String,
        keyToCheck: String
    ): UsuariosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/usuarioEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("apikey", keyToCheck)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<UsuariosDto>(respuestaConValidacion)
        } else {
            UsuariosDto(Validacion = validacion)
        }
    }



    // === Add Rol ===
    suspend fun addRol(
        apiKey: String, permiso: String, metodo: String,
        descripcion: String
    ): RolesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/rolesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("descripcion", descripcion)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<RolesDto>(respuestaConValidacion)
        } else {
            RolesDto(Validacion = validacion)
        }
    }

    // === Update Rol ===
    suspend fun updateRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, descripcion: String
    ): RolesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/rolesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("rolId", rolId)
                append("descripcion", descripcion)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<RolesDto>(respuestaConValidacion)
        } else {
            RolesDto(Validacion = validacion)
        }
    }

    // === Delete Rol ===
    suspend fun deleteRol(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ): RolesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/rolesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("rolId", rolId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<RolesDto>(respuestaConValidacion)
        } else {
            RolesDto(Validacion = validacion)
        }
    }

    // === Get Rol By ID ===
    suspend fun getRolById(
        apiKey: String, permiso: String, metodo: String,
        rolId: String
    ): RolesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/rolesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("rolId", rolId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<RolesDto>(respuestaConValidacion)
        } else {
            RolesDto(Validacion = validacion)
        }
    }

    // === Get All Roles ===
    suspend fun getAllRoles(
        apiKey: String, permiso: String, metodo: String
    ): List<RolesDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/rolesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("roles")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<RolesDto>(respuestaConValidacion)
        }
    }


    // === Herramientas ===

    // === Add Herramienta ===
    suspend fun addHerramienta(
        apiKey: String, permiso: String, metodo: String,
        icono: String, nombre: String, creador: String, estado: String
    ): HerramientasDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/herramientasEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("icono", icono)
                append("nombre", nombre)
                append("creador", creador)
                append("estado", estado)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<HerramientasDto>(respuestaConValidacion)
        } else {
            HerramientasDto(Validacion = validacion)
        }
    }

    // === Update Herramienta ===
    suspend fun updateHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, icono: String, nombre: String, creador: String, estado: String
    ): HerramientasDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/herramientasEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("herramientaId", herramientaId)
                append("icono", icono)
                append("nombre", nombre)
                append("creador", creador)
                append("estado", estado)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<HerramientasDto>(respuestaConValidacion)
        } else {
            HerramientasDto(Validacion = validacion)
        }
    }

    // === Delete Herramienta ===
    suspend fun deleteHerramienta(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ): HerramientasDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/herramientasEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("herramientaId", herramientaId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<HerramientasDto>(respuestaConValidacion)
        } else {
            HerramientasDto(Validacion = validacion)
        }
    }

    // === Get Herramienta ===
    suspend fun getHerramientaById(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String
    ): HerramientasDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/herramientasEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("herramientaId", herramientaId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<HerramientasDto>(respuestaConValidacion)
        } else {
            HerramientasDto(Validacion = validacion)
        }
    }

    // === Get All Herramientas ===
    suspend fun getAllHerramientas(
        apiKey: String, permiso: String, metodo: String
    ): List<HerramientasDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/herramientasEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("herramientas")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<HerramientasDto>(respuestaConValidacion)
        }
    }



    // === Suscripciones ===

    // === Add Suscripcion ===
    suspend fun addSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        herramientaId: String, usuarioId: String, pagoId: String,
        descripcion: String, fechaInicio: String, fechaFin: String
    ): SuscripcionesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("herramientaId", herramientaId)
                append("usuarioId", usuarioId)
                append("pagoId", pagoId)
                append("descripcion", descripcion)
                append("fechaInicio", fechaInicio)
                append("fechaFin", fechaFin)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        } else {
            SuscripcionesDto(Validacion = validacion)
        }
    }

    // === Update Suscricion ===
    suspend fun updateSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String, herramientaId: String, usuarioId: String,
        pagoId: String, descripcion: String, fechaInicio: String, fechaFin: String
    ): SuscripcionesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("suscripcionId", suscripcionId)
                append("herramientaId", herramientaId)
                append("usuarioId", usuarioId)
                append("pagoId", pagoId)
                append("descripcion", descripcion)
                append("fechaInicio", fechaInicio)
                append("fechaFin", fechaFin)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        } else {
            SuscripcionesDto(Validacion = validacion)
        }
    }

    // === Delete Suscripcion ===
    suspend fun deleteSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ): SuscripcionesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("suscripcionId", suscripcionId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        } else {
            SuscripcionesDto(Validacion = validacion)
        }
    }

    // === Get Suscripcion ===
    suspend fun getSuscripcionById(
        apiKey: String, permiso: String, metodo: String,
        suscripcionId: String
    ): SuscripcionesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("suscripcionId", suscripcionId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        } else {
            SuscripcionesDto(Validacion = validacion)
        }
    }

    // === Get All Suscripciones ===
    suspend fun getAllSuscripciones(
        apiKey: String, permiso: String, metodo: String
    ): List<SuscripcionesDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()
        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("suscripciones")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        }
    }


    // === Check Suscripcion ===
    suspend fun checkSuscripcion(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, herramientaId: String
    ): SuscripcionesDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/suscripcionesEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("usuarioId", usuarioId)
                append("herramientaId", herramientaId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<SuscripcionesDto>(respuestaConValidacion)
        } else {
            SuscripcionesDto(Validacion = validacion)
        }
    }



    // === Pagos ===

    // === Add Pago ===
    suspend fun addPago(
        apiKey: String, permiso: String, metodo: String,
        usuarioId: String, descripcion: String, metodoPago: String,
        monto: String, fecha: String
    ): PagosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/pagosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("usuarioId", usuarioId)
                append("descripcion", descripcion)
                append("metodo", metodoPago)
                append("monto", monto)
                append("fecha", fecha)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PagosDto>(respuestaConValidacion)
        } else {
            PagosDto(Validacion = validacion)
        }
    }

    // === Update Pago ===
    suspend fun updatePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String, usuarioId: String, descripcion: String,
        metodoPago: String, monto: String, fecha: String
    ): PagosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/pagosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("pagoId", pagoId)
                append("usuarioId", usuarioId)
                append("descripcion", descripcion)
                append("metodo", metodoPago)
                append("monto", monto)
                append("fecha", fecha)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PagosDto>(respuestaConValidacion)
        } else {
            PagosDto(Validacion = validacion)
        }
    }

    // === Delete Pago ===
    suspend fun deletePago(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ): PagosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/pagosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("pagoId", pagoId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PagosDto>(respuestaConValidacion)
        } else {
            PagosDto(Validacion = validacion)
        }
    }

    // === Get Pago ===
    suspend fun getPagoById(
        apiKey: String, permiso: String, metodo: String,
        pagoId: String
    ): PagosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/pagosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("pagoId", pagoId)
            }
        ).body()
        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PagosDto>(respuestaConValidacion)
        } else {
            PagosDto(Validacion = validacion)
        }
    }

    // === Get All Pago ===
    suspend fun getAllPagos(
        apiKey: String, permiso: String, metodo: String
    ): List<PagosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/pagosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()
        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("pagos")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<PagosDto>(respuestaConValidacion)
        }
    }


    // == PERMISOS ==


    // === Add Permiso ===
    suspend fun addPermiso(
        apiKey: String, permiso: String, metodo: String,
        nombre: String, descripcion: String
    ): PermisosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/permisosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("nombre", nombre)
                append("descripcion", descripcion)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PermisosDto>(respuestaConValidacion)
        } else {
            PermisosDto(Validacion = validacion)
        }
    }

    // === Update Permite ===
    suspend fun updatePermiso(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String, nombre: String, descripcion: String
    ): PermisosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/permisosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("permisoId", permisoId)
                append("nombre", nombre)
                append("descripcion", descripcion)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PermisosDto>(respuestaConValidacion)
        } else {
            PermisosDto(Validacion = validacion)
        }
    }

    // === Delete Permite ===
    suspend fun deletePermiso(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String
    ): PermisosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/permisosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("permisoId", permisoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PermisosDto>(respuestaConValidacion)
        } else {
            PermisosDto(Validacion = validacion)
        }
    }

    // === Get Permite By ID ===
    suspend fun getPermisoById(
        apiKey: String, permiso: String, metodo: String,
        permisoId: String
    ): PermisosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/permisosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("permisoId", permisoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<PermisosDto>(respuestaConValidacion)
        } else {
            PermisosDto(Validacion = validacion)
        }
    }

    // === Get All Permite ===
    suspend fun getAllPermisos(
        apiKey: String, permiso: String, metodo: String
    ): List<PermisosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/permisosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("permisos")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<PermisosDto>(respuestaConValidacion)
        }
    }



    // === Accesos ===

    // === Add Acceso ===
    suspend fun addAcceso(
        apiKey: String, permiso: String, metodo: String,
        rolId: String, permisoId: String
    ): AccesosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/accesosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("rolId", rolId)
                append("permisoId", permisoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<AccesosDto>(respuestaConValidacion)
        } else {
            AccesosDto(Validacion = validacion)
        }
    }

    // === Update Acceso ===
    suspend fun updateAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String, rolId: String, permisoId: String
    ): AccesosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/accesosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("accesoId", accesoId)
                append("rolId", rolId)
                append("permisoId", permisoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<AccesosDto>(respuestaConValidacion)
        } else {
            AccesosDto(Validacion = validacion)
        }
    }

    // === Delete Acceso ===
    suspend fun deleteAcceso(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ): AccesosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/accesosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("accesoId", accesoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<AccesosDto>(respuestaConValidacion)
        } else {
            AccesosDto(Validacion = validacion)
        }
    }

    // === Get Acceso By ID ===
    suspend fun getAccesoById(
        apiKey: String, permiso: String, metodo: String,
        accesoId: String
    ): AccesosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/accesosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("accesoId", accesoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<AccesosDto>(respuestaConValidacion)
        } else {
            AccesosDto(Validacion = validacion)
        }
    }

    // === Get All Accesos ===
    suspend fun getAllAccesos(
        apiKey: String, permiso: String, metodo: String
    ): List<AccesosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/accesosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject

        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("accesos")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<AccesosDto>(respuestaConValidacion)
        }
    }



    // === Codigos ===

    // === Add Codigo ===
    suspend fun addCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String, descripcion: String, expiracion: String, estado: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("codigo", codigo)
                append("descripcion", descripcion)
                append("expiracion", expiracion)
                append("estado", estado)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }

    // === Update Codigo ===
    suspend fun updateCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String, codigo: String, descripcion: String, expiracion: String, estado: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("codigoId", codigoId)
                append("codigo", codigo)
                append("descripcion", descripcion)
                append("expiracion", expiracion)
                append("estado", estado)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }

    // === Delete Codigo ===
    suspend fun deleteCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("codigoId", codigoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }

    // === Get Codigo By ID ===
    suspend fun getCodigoById(
        apiKey: String, permiso: String, metodo: String,
        codigoId: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("codigoId", codigoId)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }

    // === Get All Codigos ===
    suspend fun getAllCodigos(
        apiKey: String, permiso: String, metodo: String
    ): List<CodigosDto> {
        val responseText: String = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
            }
        ).body()

        val jsonElement = Json.parseToJsonElement(responseText).jsonObject
        val validacion = jsonElement["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = jsonElement["Respuesta"]?.jsonObject
        val array = respuesta?.get("codigos")?.jsonArray ?: return emptyList()

        return array.map { data ->
            val respuestaConValidacion = buildJsonObject {
                data.jsonObject.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }
            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        }
    }

    // === Verificar Codigo ===
    suspend fun verificarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("codigo", codigo)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }

    // === Usar Codigo ===
    suspend fun usarCodigo(
        apiKey: String, permiso: String, metodo: String,
        codigo: String
    ): CodigosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/codigosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)
                append("codigo", codigo)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CodigosDto>(respuestaConValidacion)
        } else {
            CodigosDto(Validacion = validacion)
        }
    }


    suspend fun sendCorreo(
        apiKey: String, permiso: String, metodo: String,
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
    ): CorreosDto {
        val responseText: JsonElement = client.submitForm(
            url = "$baseUrl/api/private/correosEndpoint/",
            formParameters = Parameters.build {
                append("apikey", apiKey)
                append("permiso", permiso)
                append("metodo", metodo)

                append("smtpHost", smtpHost)
                append("smtpUsername", smtpUsername)
                append("smtpPassword", smtpPassword)
                append("smtpPort", smtpPort.toString())
                append("asunto", asunto)
                append("mensaje", mensaje)
                append("piePagina", piePagina)
                append("destinatario", destinatario)
                append("nombreEmpresa", nombreEmpresa)
                append("direccionEmpresa", direccionEmpresa)
            }
        ).body()

        val validacion = responseText.jsonObject["Validacion"]?.jsonPrimitive?.contentOrNull
        val respuesta = responseText.jsonObject["Respuesta"]?.jsonObject

        return if (respuesta != null) {
            val respuestaConValidacion = buildJsonObject {
                respuesta.forEach { (key, value) ->
                    put(key, value)
                }
                put("Validacion", JsonPrimitive(validacion ?: ""))
            }

            Json.decodeFromJsonElement<CorreosDto>(respuestaConValidacion)
        } else {
            CorreosDto(Validacion = validacion)
        }
    }

}
