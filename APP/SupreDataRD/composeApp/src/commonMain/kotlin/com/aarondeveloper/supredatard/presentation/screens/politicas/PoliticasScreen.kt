package com.aarondeveloper.supredatard.presentation.screens.politicas

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.presentation.component.AsciiText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PoliticasScreen() {

    PoliticasBodyScreen()
}
@Composable
fun PoliticasBodyScreen() {

    val scrollState = rememberScrollState()

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
                        text = "terminal — Terminos & Condiciones",
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
                        texto = "TERMS",
                        fontSize = 2.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )
                    AsciiText(
                        texto = "&",
                        fontSize = 2.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )
                    AsciiText(
                        texto = "CONDITIONS",
                        fontSize = 2.sp,
                        spacing = 8.dp,
                        background = Color.Transparent,
                        alignment = "central"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        Text(
                            text = """
        Al registrarte en nuestra aplicación, aceptas los siguientes términos y condiciones:

        1. USO DE LA APLICACIÓN
        La aplicación está diseñada para uso personal y no comercial. Te comprometes a usarla de manera responsable y a no infringir los derechos de otros usuarios.

        2. PRIVACIDAD Y DATOS PERSONALES
        Toda información que proporciones será tratada de forma confidencial. Aceptas que podamos almacenar y procesar tus datos para el correcto funcionamiento de la app.

        3. CUENTA DE USUARIO
        Eres responsable de mantener tu información de acceso segura. No compartas tu contraseña y notifícanos si detectas algún uso no autorizado.

        4. CONTENIDO Y PROPIEDAD INTELECTUAL
        Todo el contenido de la aplicación, incluyendo texto, imágenes y logos, es propiedad de la empresa y no puede ser reproducido sin autorización.

        5. RESPONSABILIDAD
        No nos hacemos responsables de pérdidas, daños o interrupciones que puedan surgir del uso de la aplicación. El uso es bajo tu propio riesgo.

        6. MODIFICACIONES
        Nos reservamos el derecho de modificar los términos y condiciones en cualquier momento. Las modificaciones se notificarán a través de la aplicación o correo electrónico.

        7. CONTACTO
        Si tienes dudas sobre los términos, puedes contactarnos a través del correo soporte@tuapp.com o mediante la sección de contacto de la app.

        Al hacer clic, confirmas que has leído y aceptado estos términos y condiciones.
    """.trimIndent(),
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun PoliticasScreenPreview() {

}

