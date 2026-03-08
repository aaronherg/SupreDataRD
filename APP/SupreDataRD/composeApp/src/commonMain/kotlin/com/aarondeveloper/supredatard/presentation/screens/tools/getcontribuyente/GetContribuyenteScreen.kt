package com.aarondeveloper.supredatard.presentation.screens.tools.getcontribuyente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.presentation.component.AsciiText
import com.aarondeveloper.supredatard.presentation.component.Button
import com.aarondeveloper.supredatard.presentation.component.Cargando
import com.aarondeveloper.supredatard.presentation.component.DatosInfoRow
import com.aarondeveloper.supredatard.presentation.component.Input
import com.aarondeveloper.supredatard.presentation.component.NavItem
import com.aarondeveloper.supredatard.presentation.component.NavigationBar
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.aarondeveloper.dealerpos.ui.theme.VerdeModernoEnd
import org.aarondeveloper.dealerpos.ui.theme.VerdeModernoStart
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import supredatard.composeapp.generated.resources.Res
import supredatard.composeapp.generated.resources.btn_1
import supredatard.composeapp.generated.resources.btn_2
import supredatard.composeapp.generated.resources.btn_4
import supredatard.composeapp.generated.resources.ico_check


@OptIn(KoinExperimentalAPI::class)
@Composable
fun GetContribuyenteScreen(
    viewModel: GetContribuyenteViewModel = koinViewModel(),
    onNoAutenticado: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    onInicioClientClick: () -> Unit = {},
    onInicioAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {}
){
    LaunchedEffect(Unit){
        viewModel.verificarAutenticacion(onNoAutenticado)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GetContribuyenteBodyScreen(
        uiState = uiState,
        onSalirClick = onSalirClick,
        onInicioClientClick = onInicioClientClick,
        onInicioAdminClick = onInicioAdminClick,
        onPerfilClick = onPerfilClick,
        onCerrarSesionClick = viewModel::onCerrarSesionClick,
        verificarRol = viewModel::verificarRol,
        onBuscarConstribuyenteClick = viewModel::onBuscarConstribuyenteClick,
        onRNCChange =viewModel::onRNCChange
    )
}

@Composable
fun GetContribuyenteBodyScreen(
    uiState: UiState,
    onSalirClick: () -> Unit = {},
    onInicioClientClick: () -> Unit = {},
    onInicioAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onCerrarSesionClick: (() -> Unit) -> Unit,
    verificarRol: (() -> Unit, () -> Unit) -> Unit,
    onBuscarConstribuyenteClick: () -> Unit = {},
    onRNCChange: (String) -> Unit
) {

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.ocultarTeclado) {
        if (uiState.ocultarTeclado) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

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
                            Box(
                                Modifier
                                    .size(12.dp)
                                    .background(Color(0xFFFF5F57), CircleShape)
                            )
                            Box(
                                Modifier
                                    .size(12.dp)
                                    .background(Color(0xFFFFBD2E), CircleShape)
                            )
                            Box(
                                Modifier
                                    .size(12.dp)
                                    .background(Color(0xFF28C840), CircleShape)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "terminal — Buscar Contribuyente",
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
                    )
                    {
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
                                texto = "BUSCAR",
                                fontSize = 3.sp,
                                spacing = 8.dp,
                                background = Color.Transparent,
                                alignment = "central"
                            )

                            AsciiText(
                                texto = "RNC",
                                fontSize = 3.sp,
                                spacing = 8.dp,
                                background = Color.Transparent,
                                alignment = "central"
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        Input(
                                            value = uiState.nRNC ?: "",
                                            onValueChange = { nRNC -> onRNCChange(nRNC) },
                                            label = "Registro Nacional de Contribuyentes (RNC)",
                                            borderRadius = 10,
                                            borderColor = Brush.horizontalGradient(
                                                listOf(
                                                    MoradoStart,
                                                    MarronEnd
                                                )
                                            ),
                                            borderSize = 2,
                                            textSize = 12,
                                            verticalPadding = 14,
                                            type = "Number",
                                            isPassword = false,
                                            isDisabled = false,
                                        )
                                    }

                                    Button(
                                        text = "Buscar",
                                        onClick = { onBuscarConstribuyenteClick() },
                                        borderRadius = 10,
                                        borderSize = 2,
                                        verticalPadding = 17,
                                        borderColor = Brush.horizontalGradient(listOf(VerdeModernoStart, VerdeModernoEnd)),
                                        textSize = 10,
                                        modifier = Modifier
                                            .width(60.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                }

                                if(uiState.encontrado) {

                                    Text(
                                        text = "<<< DATOS OFICIALES >>>",
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        fontFamily = FontFamily.Monospace,
                                        lineHeight = 20.sp,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        DatosInfoRow(label = "Registro Nacional de Contribuyentes (RNC)", value = uiState.getContribuyenteDto!!.RNC.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Razon Social", value = uiState.getContribuyenteDto!!.razonSocial.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Regimen de Pagos", value = uiState.getContribuyenteDto!!.regimenPagos.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Categoria", value = uiState.getContribuyenteDto!!.categoria.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Estado", value = uiState.getContribuyenteDto!!.estado.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Nombre Comercial", value = uiState.getContribuyenteDto!!.nombreComercial.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Facturador Electronico", value = uiState.getContribuyenteDto!!.facturadorElectronico.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Licencias VHM", value = uiState.getContribuyenteDto!!.licenciasVHM.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Actividad Economica", value = uiState.getContribuyenteDto!!.actividadEconomica.toString().trim() ?: "Desconocida")
                                        DatosInfoRow(label = "Administracion Local", value = uiState.getContribuyenteDto!!.administracionLocal.toString().trim() ?: "Desconocida")
                                    }

                                }
                                else{
                                    GifImage(
                                        modifier = Modifier.fillMaxSize(),
                                        drawable = "gif_buscando",
                                        scaleMode = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationBar(
                    items = listOf(
                        NavItem("Salir", Res.drawable.btn_2) { onCerrarSesionClick(onSalirClick) },
                        NavItem("Inicio", Res.drawable.btn_1) { verificarRol(onInicioClientClick, onInicioAdminClick) },
                        NavItem("Perfil", Res.drawable.btn_4) { onPerfilClick() }
                    ),
                    initialSelectedItem = "Inicio",
                    onItemSelected = { selected ->  }
                )
            }
        }

        if (uiState.cargando) {
            Cargando()
        }
    }
}


@Preview
@Composable
fun GetContribuyenteScreenPreview() {
    GetContribuyenteScreen()
}
