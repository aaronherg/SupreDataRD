package com.aarondeveloper.supredatard.presentation.screens.login

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.presentation.component.AsciiText
import com.aarondeveloper.supredatard.presentation.component.Button
import com.aarondeveloper.supredatard.presentation.component.Cargando
import com.aarondeveloper.supredatard.presentation.component.Input
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.aarondeveloper.dealerpos.ui.theme.VerdeModernoEnd
import org.aarondeveloper.dealerpos.ui.theme.VerdeModernoStart
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onAutenticadoMainClient: () -> Unit,
    onAutenticadoMainAdmin: () -> Unit,
    onNavOlvidasteContrasenaClick: () -> Unit,
    onNavRegistrarseClick: () -> Unit
) {
    LaunchedEffect(Unit){
        viewModel.verificarAutenticacion(onAutenticadoMainClient, onAutenticadoMainAdmin)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginBodyScreen(
        uiState = uiState,
        onAutenticadoMainClient = onAutenticadoMainClient,
        onAutenticadoMainAdmin = onAutenticadoMainAdmin,
        onNavOlvidasteContrasenaClick = onNavOlvidasteContrasenaClick,
        onNavRegistrarseClick = onNavRegistrarseClick,
        onCorreoChange = viewModel::onCorreoChange,
        onContrasenaChange = viewModel::onContrasenaChange,
        onOlvideContrasenaClick = viewModel::onOlvideContrasenaClick,
        onRegistrarseClick = viewModel::onRegistrarseClick,
        onIniciarSesionClick = viewModel::onIniciarSesionClick,
    )
}
@Composable
fun LoginBodyScreen(
    uiState: UiState,
    onAutenticadoMainClient: () -> Unit,
    onAutenticadoMainAdmin: () -> Unit,
    onNavOlvidasteContrasenaClick: () -> Unit,
    onNavRegistrarseClick: () -> Unit,
    onCorreoChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onOlvideContrasenaClick: (() -> Unit) -> Unit,
    onRegistrarseClick: (() -> Unit) -> Unit,
    onIniciarSesionClick: (() -> Unit, () -> Unit) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        GifImage(
            modifier = Modifier.fillMaxSize(),
            drawable = "gif_binario",
            scaleMode = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(12.dp))
        ) {
            GifImage(
                modifier = Modifier
                    .matchParentSize(),
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.White.copy(alpha = 0.02f), Color.Black.copy(alpha = 0.12f))
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
                        text = "terminal — Login",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                val scrollState = rememberScrollState()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    AsciiText(
                        texto = "LOGIN",
                        fontSize = 6.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Input(
                            value = uiState.correo ?: "",
                            onValueChange = { correo -> onCorreoChange(correo) },
                            label = "Ingrese su Correo Electronico",
                            borderRadius = 10,
                            borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                            borderSize = 2,
                            textSize = 12,
                            verticalPadding = 16,
                            type = "Text",
                            isPassword = false,
                            isDisabled = false
                        )

                        Input(
                            value = uiState.contrasena ?: "",
                            onValueChange = { contrasena -> onContrasenaChange(contrasena) },
                            label = "Ingrese su Contraseña",
                            borderRadius = 10,
                            borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                            borderSize = 2,
                            textSize = 12,
                            verticalPadding = 16,
                            type = "Text",
                            isPassword = true,
                            isDisabled = false
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Registrarse",
                                style = TextStyle(
                                    brush = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.clickable { onRegistrarseClick(onNavRegistrarseClick) }
                            )
                            Text(
                                text = "Recuperar Contraseña",
                                style = TextStyle(
                                    brush = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.clickable { onOlvideContrasenaClick(onNavOlvidasteContrasenaClick) }
                            )
                        }

                        Button(
                            text = "Acceder",
                            onClick = { onIniciarSesionClick(onAutenticadoMainAdmin, onAutenticadoMainClient) },
                            borderRadius = 16,
                            borderSize = 2,
                            borderColor = Brush.horizontalGradient(listOf(VerdeModernoStart, VerdeModernoEnd)),
                            textSize = 16
                        )
                    }
                }
            }
        }
    }

    if (uiState.cargando) {
        Cargando()
    }
}



@Preview
@Composable
fun LoginScreenPreview() {

}

