package com.aarondeveloper.supredatard.presentation.screens.main.client.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.aarondeveloper.supredatard.presentation.component.NavItem
import com.aarondeveloper.supredatard.presentation.component.NavigationBar
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
import supredatard.composeapp.generated.resources.Res
import supredatard.composeapp.generated.resources.btn_1
import supredatard.composeapp.generated.resources.btn_2
import supredatard.composeapp.generated.resources.btn_4


@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeClientScreen(
    viewModel: HomeClientViewModel = koinViewModel(),
    onNoAutenticado: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    onInicioClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onGetCiudadanoClick: () -> Unit = {},
    onGetVehiculoClick: () -> Unit = {},
    onGetLicenciaClick: () -> Unit = {},
    onGetContribuyenteClick: () -> Unit = {},
    onGetFotoClick: () -> Unit = {}
){

    LaunchedEffect(Unit){
        viewModel.verificarAutenticacion(onNoAutenticado)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeClientBodyScreen(
        uiState = uiState,
        onSalirClick = onSalirClick,
        onInicioClick = onInicioClick,
        onPerfilClick = onPerfilClick,
        onGetCiudadanoClick = onGetCiudadanoClick,
        onGetVehiculoClick = onGetVehiculoClick,
        onGetLicenciaClick = onGetLicenciaClick,
        onGetContribuyenteClick = onGetContribuyenteClick,
        onGetFotoClick = onGetFotoClick,
        onCerrarSesionClick = viewModel::onCerrarSesionClick
    )
}

@Composable
fun HomeClientBodyScreen(
    uiState: UiState,
    onSalirClick: () -> Unit = {},
    onInicioClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onGetCiudadanoClick: () -> Unit = {},
    onGetVehiculoClick: () -> Unit = {},
    onGetLicenciaClick: () -> Unit = {},
    onGetContribuyenteClick: () -> Unit = {},
    onGetFotoClick: () -> Unit = {},
    onCerrarSesionClick: (() -> Unit) -> Unit
) {
    val scrollState = rememberScrollState()

    val estadoMap = uiState.listaHerramientasEstado
        ?.associate { it.herramientaId to it.suscrito }
        ?: emptyMap()

    val herramientasConEstado = uiState.listaHerramientasDto
        ?.mapNotNull { herramienta ->
            val suscrito = estadoMap[herramienta.herramientaId.toString()] ?: return@mapNotNull null
            val estado = if (suscrito == "true") "Activa" else "Inactiva"
            herramienta.copy(estado = estado)
        } ?: emptyList()

    Box(modifier = Modifier.fillMaxSize()) {

        GifImage(
            modifier = Modifier.fillMaxSize(),
            drawable = "gif_binario",
            scaleMode = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp, vertical = 20.dp)
                .offset(x = (-5).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .weight(1.4f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF0A0A0C).copy(alpha = 0.85f))
                    .border(1.2.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(18.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.White.copy(alpha = 0.04f),
                                        Color.Black.copy(alpha = 0.1f)
                                    )
                                )
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(Modifier.size(12.dp).background(Color(0xFFFF5F57), CircleShape))
                            Box(Modifier.size(12.dp).background(Color(0xFFFFBD2E), CircleShape))
                            Box(Modifier.size(12.dp).background(Color(0xFF28C840), CircleShape))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "terminal — Main",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                            .verticalScroll(scrollState)
                    ) {
                        GifImage(
                            modifier = Modifier.matchParentSize(),
                            drawable = "gif_cerebro",
                            scaleMode = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(Color(0xFF0A0A0C).copy(alpha = 0.75f))
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color.White.copy(alpha = 0.03f), RoundedCornerShape(12.dp))
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            AsciiText(
                                texto = "MAIN",
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationBar(
                    items = listOf(
                        NavItem("Salir", Res.drawable.btn_2) { onCerrarSesionClick(onSalirClick) },
                        NavItem("Inicio", Res.drawable.btn_1) { onInicioClick() },
                        NavItem("Perfil", Res.drawable.btn_4) { onPerfilClick() }
                    ),
                    initialSelectedItem = "Inicio",
                    onItemSelected = { selected ->  }
                )
            }
        }
    }

    if (uiState.cargando) {
        Cargando()
    }
}





@Preview
@Composable
fun HomeClientScreenPreview() {
    HomeClientScreen()
}
