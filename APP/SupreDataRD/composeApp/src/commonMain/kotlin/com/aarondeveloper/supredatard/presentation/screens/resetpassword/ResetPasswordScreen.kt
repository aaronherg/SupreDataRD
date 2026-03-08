package com.aarondeveloper.supredatard.presentation.screens.resetpassword

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = koinViewModel(),
    onNavFinalizarResetPasswordClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ResetPasswordBodyScreen(
        uiState = uiState,
        onFinalizarResetPasswordClick = viewModel::onFinalizarResetPasswordClick,
        onNavFinalizarResetPasswordClick = onNavFinalizarResetPasswordClick,
        onCorreoElectronicoChange = viewModel::onCorreoElectronicoChange,
        onPrimerCodigoChange = viewModel::onPrimerCodigoChange,
        onSegundoCodigoChange = viewModel::onSegundoCodigoChange,
        onTercerCodigoChange = viewModel::onTercerCodigoChange,
        onCuartoCodigoChange = viewModel::onCuartoCodigoChange,
        onQuintoCodigoChange = viewModel::onQuintoCodigoChange,
        onSextoCodigoChange = viewModel::onSextoCodigoChange,
        onContrasenaChange = viewModel::onContrasenaChange,
        onRepetirContrasenaChange = viewModel::onRepetirContrasenaChange,
        onPrimeraFaseClick = viewModel::onPrimeraFaseClick,
        onSegundaFaseClick = viewModel::onSegundaFaseClick
    )
}
@Composable
fun ResetPasswordBodyScreen(
    uiState: UiState,
    onFinalizarResetPasswordClick: (() -> Unit) -> Unit,
    onNavFinalizarResetPasswordClick: () -> Unit,
    onCorreoElectronicoChange: (String) -> Unit,
    onPrimerCodigoChange: (String) -> Unit,
    onSegundoCodigoChange: (String) -> Unit,
    onTercerCodigoChange: (String) -> Unit,
    onCuartoCodigoChange: (String) -> Unit,
    onQuintoCodigoChange: (String) -> Unit,
    onSextoCodigoChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onRepetirContrasenaChange: (String) -> Unit,
    onPrimeraFaseClick: () -> Unit,
    onSegundaFaseClick: () -> Unit
) {

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequesters = remember { List(6) { FocusRequester() } }

    LaunchedEffect(uiState.ocultarTeclado) {
        if (uiState.ocultarTeclado) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

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
                .padding(horizontal = 16.dp, vertical = 100.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(12.dp))
        ) {


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
                        text = "terminal — Register",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    AsciiText(
                        texto = "RESET",
                        fontSize = 3.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )

                    AsciiText(
                        texto = "PASSWORD",
                        fontSize = 3.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if(uiState.primeraFase){
                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "<< CORREO ELECTRONICO ASOCIADO >>",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )

                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Input(
                                value = uiState.correoElectronico ?: "",
                                onValueChange = { correoElectronico -> onCorreoElectronicoChange(correoElectronico) },
                                label = "Correo Electronico",
                                borderRadius = 10,
                                borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                borderSize = 2,
                                textSize = 12,
                                verticalPadding = 16,
                                type = "Text",
                                isPassword = false,
                                isDisabled = false
                            )

                            Button(
                                text = "Continuar (1/3)",
                                onClick = { onPrimeraFaseClick() },
                                borderRadius = 16,
                                borderSize = 2,
                                borderColor = Brush.horizontalGradient(listOf(VerdeModernoStart, VerdeModernoEnd)),
                                textSize = 12
                            )
                        }

                        if(uiState.segundaFase){
                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "<< CODIGO DE VERIFICACION >>",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )

                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val codigos = listOf(
                                    uiState.primerCodigo ?: "",
                                    uiState.segundoCodigo ?: "",
                                    uiState.tercerCodigo ?: "",
                                    uiState.cuartoCodigo ?: "",
                                    uiState.quintoCodigo ?: "",
                                    uiState.sextoCodigo ?: ""
                                )

                                codigos.forEachIndexed { index, valor ->
                                    Input(
                                        value = valor,
                                        onValueChange = { v ->
                                            val nuevoValor = v.take(1)

                                            when(index) {
                                                0 -> onPrimerCodigoChange(nuevoValor)
                                                1 -> onSegundoCodigoChange(nuevoValor)
                                                2 -> onTercerCodigoChange(nuevoValor)
                                                3 -> onCuartoCodigoChange(nuevoValor)
                                                4 -> onQuintoCodigoChange(nuevoValor)
                                                5 -> onSextoCodigoChange(nuevoValor)

                                            }

                                            if (nuevoValor.isNotEmpty() && index < focusRequesters.size - 1) {
                                                focusRequesters[index + 1].requestFocus()
                                            }

                                            if (nuevoValor.isEmpty() && valor.isNotEmpty() && index > 0) {
                                                focusRequesters[index - 1].requestFocus()
                                            }

                                            if (index == 5 && nuevoValor.isNotEmpty()) {
                                                keyboardController?.hide()
                                                focusManager.clearFocus(force = true)
                                            }
                                        },
                                        label = "0",
                                        modifier = Modifier
                                            .weight(1f)
                                            .focusRequester(focusRequesters[index]),
                                        borderRadius = 10,
                                        borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                        borderSize = 2,
                                        textSize = 10,
                                        alineacion = TextAlign.Center,
                                        horizontalPadding = 10,
                                        verticalPadding = 10,
                                        type = "Number",
                                        isPassword = false,
                                        isDisabled = false
                                    )
                                }
                            }


                            Button(
                                text = "Continuar (2/3)",
                                onClick = { onSegundaFaseClick() },
                                borderRadius = 16,
                                borderSize = 2,
                                borderColor = Brush.horizontalGradient(listOf(VerdeModernoStart, VerdeModernoEnd)),
                                textSize = 12
                            )
                        }
                        if(uiState.terceraFase){
                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "<< RESTABLECER SU CONTRASEÑA >>",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Monospace
                            )

                            HorizontalDivider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Input(
                                value = uiState.contrasena ?: "",
                                onValueChange = { contrasena -> onContrasenaChange(contrasena) },
                                label = "Contraseña",
                                borderRadius = 10,
                                borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                borderSize = 2,
                                textSize = 12,
                                verticalPadding = 16,
                                type = "Text",
                                isPassword = true,
                                isDisabled = false
                            )

                            Input(
                                value = uiState.repetirContrasena ?: "",
                                onValueChange = { repetirContrasena -> onRepetirContrasenaChange(repetirContrasena) },
                                label = "Repitir la Contraseña",
                                borderRadius = 10,
                                borderColor = Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)),
                                borderSize = 2,
                                textSize = 12,
                                verticalPadding = 16,
                                type = "Text",
                                isPassword = true,
                                isDisabled = false
                            )

                            Button(
                                text = "Completar (3/3)",
                                onClick = { onFinalizarResetPasswordClick(onNavFinalizarResetPasswordClick) },
                                borderRadius = 16,
                                borderSize = 2,
                                borderColor = Brush.horizontalGradient(listOf(VerdeModernoStart, VerdeModernoEnd)),
                                textSize = 12
                            )
                        }
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
fun ResetPasswordScreenPreview() {

}

