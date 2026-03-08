package com.aarondeveloper.supredatard.presentation.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.presentation.component.AsciiText
import com.aarondeveloper.supredatard.presentation.component.Cargando
import com.aarondeveloper.supredatard.presentation.component.Notificacion
import com.aarondeveloper.supredatard.presentation.component.NotificationType
import com.aarondeveloper.supredatard.presentation.component.ToolCard
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.aarondeveloper.dealerpos.ui.theme.PrimaryText
import org.aarondeveloper.dealerpos.ui.theme.Purple80
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun ToolsScreen(
    viewModel: ToolsViewModel = koinViewModel(),
    onGetCiudadanoClick: () -> Unit = {},
    onGetVehiculoClick: () -> Unit = {},
    onGetLicenciaClick: () -> Unit = {},
    onGetContribuyenteClick: () -> Unit = {},
    onGetFotoClick: () -> Unit = {}
){

    LaunchedEffect(Unit){
        viewModel.getAllHerramientas()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ToolsBodyScreen(
        uiState = uiState,
        onGetCiudadanoClick = onGetCiudadanoClick,
        onGetVehiculoClick = onGetVehiculoClick,
        onGetLicenciaClick = onGetLicenciaClick,
        onGetContribuyenteClick = onGetContribuyenteClick,
        onGetFotoClick = onGetFotoClick
    )
}

@Composable
fun ToolsBodyScreen(
    uiState: UiState,
    onGetCiudadanoClick: () -> Unit = {},
    onGetVehiculoClick: () -> Unit = {},
    onGetLicenciaClick: () -> Unit = {},
    onGetContribuyenteClick: () -> Unit = {},
    onGetFotoClick: () -> Unit = {}
) {

    val estadoMap = uiState.listaHerramientasEstado
        ?.associate { it.herramientaId to it.suscrito }
        ?: emptyMap()

    val herramientasConEstado = uiState.listaHerramientasDto
        ?.mapNotNull { herramienta ->
            val suscrito = estadoMap[herramienta.herramientaId.toString()] ?: return@mapNotNull null
            val estado = if (suscrito == "true") "Activa" else "Inactiva"
            herramienta.copy(estado = estado)
        } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AsciiText(
            texto = "TOOLS",
            fontSize = 6.sp,
            spacing = 8.dp,
            background = Color.Transparent,
            alignment = "central"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Integración de la interconexión de información gubernamental.",
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace,
            lineHeight = 20.sp,
        )

        Spacer(modifier = Modifier.height(24.dp))

        if(uiState.noHerramientas){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GifImage(
                        modifier = Modifier.fillMaxSize().size(300.dp),
                        drawable = "gif_buscando",
                        scaleMode = ContentScale.Fit
                    )
                }
            }
        }
        else{
            herramientasConEstado.forEach { herramienta ->
                ToolCard(
                    iconUrl = herramienta.icono ?: "",
                    name = herramienta.nombre ?: "Desconocida",
                    status = herramienta.estado ?: "",
                    borderBrush = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                    textBrush = Brush.horizontalGradient(listOf(PrimaryText, Purple80)),
                    onClick = {
                        when (herramienta.herramientaId.toString()) {
                            "1" -> {
                                if (herramienta.estado == "Activa") {
                                    onGetCiudadanoClick()
                                } else {
                                    Notificacion(
                                        "Información",
                                        "No tienes acceso a la herramienta ${herramienta.nombre}",
                                        NotificationType.Informacion
                                    )
                                }
                            }
                            "2" -> {
                                if (herramienta.estado == "Activa") {
                                    onGetFotoClick()
                                } else {
                                    Notificacion(
                                        "Información",
                                        "No tienes acceso a la herramienta ${herramienta.nombre}",
                                        NotificationType.Informacion
                                    )
                                }
                            }
                            "3" -> {
                                if (herramienta.estado == "Activa") {
                                    onGetVehiculoClick()
                                } else {
                                    Notificacion(
                                        "Información",
                                        "No tienes acceso a la herramienta ${herramienta.nombre}",
                                        NotificationType.Informacion
                                    )
                                }
                            }
                            "4" -> {
                                if (herramienta.estado == "Activa") {
                                    onGetLicenciaClick()
                                } else {
                                    Notificacion(
                                        "Información",
                                        "No tienes acceso a la herramienta ${herramienta.nombre}",
                                        NotificationType.Informacion
                                    )
                                }
                            }
                            "5" -> {
                                if (herramienta.estado == "Activa") {
                                    onGetContribuyenteClick()
                                } else {
                                    Notificacion(
                                        "Información",
                                        "No tienes acceso a la herramienta ${herramienta.nombre}",
                                        NotificationType.Informacion
                                    )
                                }
                            }
                            else -> {
                                Notificacion(
                                    "Información",
                                    "Herramienta desconocida o sin permisos",
                                    NotificationType.Informacion
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}





@Preview
@Composable
fun ToolsScreenPreview() {
    ToolsScreen()
}