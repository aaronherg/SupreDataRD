package com.aarondeveloper.supredatard.presentation.screens.profile

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.librery.convertirBase64AImageBitmap
import com.aarondeveloper.supredatard.presentation.component.NavItem
import com.aarondeveloper.supredatard.presentation.component.NavigationBar
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import supredatard.composeapp.generated.resources.Res
import supredatard.composeapp.generated.resources.btn_1
import supredatard.composeapp.generated.resources.btn_2
import supredatard.composeapp.generated.resources.btn_4
import supredatard.composeapp.generated.resources.chica
import supredatard.composeapp.generated.resources.chico


@OptIn(KoinExperimentalAPI::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onNoAutenticado: () -> Unit = {},
    onRegresar: () -> Unit = {},
    onSalirClick: () -> Unit = {},
    onInicioClientClick: () -> Unit = {},
    onInicioAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {}
){

    LaunchedEffect(Unit){
        viewModel.verificarAutenticacion(onNoAutenticado, onRegresar)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileBodyScreen(
        uiState = uiState,
        onSalirClick = onSalirClick,
        onInicioClientClick = onInicioClientClick,
        onInicioAdminClick = onInicioAdminClick,
        onPerfilClick = onPerfilClick,
        onCerrarSesionClick = viewModel::onCerrarSesionClick,
        verificarRol = viewModel::verificarRol
    )
}

@Composable
fun ProfileBodyScreen(
    uiState: UiState,
    onSalirClick: () -> Unit = {},
    onInicioClientClick: () -> Unit = {},
    onInicioAdminClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {},
    onCerrarSesionClick: (() -> Unit) -> Unit,
    verificarRol: (() -> Unit, () -> Unit) -> Unit
) {

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

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
                            text = "terminal — Perfil",
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
                            .verticalScroll(scrollState),
                        contentAlignment = Alignment.Center
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
                                .border(
                                    1.dp,
                                    Color.White.copy(alpha = 0.03f),
                                    RoundedCornerShape(12.dp)
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Spacer(modifier = Modifier.height(16.dp))

                            if (uiState.usuarioDto != null) {

                                val foto = convertirBase64AImageBitmap(uiState.usuarioDto.foto.toString())
                                val sexo = uiState.usuarioDto.sexo.toString()

                                Box(
                                    modifier = Modifier
                                        .size(160.dp)
                                        .clip(RoundedCornerShape(18.dp))
                                        .background(Color(0xFF1C1C1E).copy(alpha = 0.5f))
                                        .border(
                                            2.dp,
                                            Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                            RoundedCornerShape(25.dp)
                                        )
                                        .shadow(8.dp, RoundedCornerShape(18.dp))
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (foto != null) {
                                        Image(
                                            bitmap = foto,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .matchParentSize()
                                                .blur(20.dp)
                                                .alpha(0.4f)
                                                .clip(RoundedCornerShape(30.dp)),
                                            contentScale = ContentScale.FillBounds
                                        )

                                        Image(
                                            bitmap = foto,
                                            contentDescription = "User Profile",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(25.dp)),
                                            contentScale = ContentScale.FillBounds
                                        )
                                    } else {
                                        val imageResource = when (sexo) {
                                            "Masculino" -> painterResource(Res.drawable.chico)
                                            "Femenino" -> painterResource(Res.drawable.chica)
                                            else -> painterResource(Res.drawable.chico)
                                        }

                                        Image(
                                            painter = imageResource,
                                            contentDescription = "User Profile",
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(25.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    ProfileInfoRow(label = "Cédula", value = uiState.usuarioDto.cedula.toString())
                                    ProfileInfoRow(label = "Nombre Completo", value = uiState.usuarioDto.nombres.toString() + " " + uiState.usuarioDto.apellidos.toString())
                                    ProfileInfoRow(label = "Sexo", value = uiState.usuarioDto.sexo.toString())
                                    ProfileInfoRow(label = "Dirección", value = uiState.usuarioDto.direccion.toString())
                                }
                            } else {
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavigationBar(
                    items = listOf(
                        NavItem("Salir", Res.drawable.btn_2) {  onCerrarSesionClick(onSalirClick) },
                        NavItem("Inicio", Res.drawable.btn_1) { verificarRol(onInicioClientClick, onInicioAdminClick) },
                        NavItem("Perfil", Res.drawable.btn_4) { onPerfilClick() }
                    ),
                    initialSelectedItem = "Perfil",
                    onItemSelected = { selected ->  }
                )
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 14.sp,  style = TextStyle(Brush.horizontalGradient(listOf(MoradoStart, MarronEnd))))
        Text(text = value, fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
