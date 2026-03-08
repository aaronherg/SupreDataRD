package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import supredatard.composeapp.generated.resources.*

enum class NotificationType {
    Exitoso, Error, Advertencia, Informacion
}

private var notificacionCounter = 0
private val notificacionesGlobal = mutableStateListOf<NotificacionData>()

data class NotificacionData(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val tipo: NotificationType
)

fun Notificacion(titulo: String, descripcion: String, tipo: NotificationType) {
    notificacionCounter++
    notificacionesGlobal.add(NotificacionData(id = notificacionCounter, titulo = titulo, descripcion = descripcion, tipo = tipo))
}

@Composable
fun NotificationHost() {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, end = 10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (notificacion in notificacionesGlobal) {
                key(notificacion.id) {
                    NotificationItem(
                        title = notificacion.titulo,
                        description = notificacion.descripcion,
                        type = notificacion.tipo,
                        onDismiss = {
                            scope.launch {
                                notificacionesGlobal.remove(notificacion)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    title: String,
    description: String,
    type: NotificationType,
    onDismiss: () -> Unit
) {
    val (bgColor, icon, iconColor) = when (type) {
        NotificationType.Exitoso -> Triple(Color(0xFF4CAF50), Res.drawable.ico_check, Color.White)
        NotificationType.Error -> Triple(Color(0xFFF44336), Res.drawable.ico_error, Color.White)
        NotificationType.Advertencia -> Triple(Color(0xFFFFC107), Res.drawable.ico_advertencia, Color.Black)
        NotificationType.Informacion -> Triple(Color(0xFF2196F3), Res.drawable.ico_info, Color.White)
    }

    LaunchedEffect(title, description) {
        delay(3000)
        onDismiss()
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -50 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -50 })
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = 240.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .background(bgColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )

            Column {
                Text(
                    text = title,
                    color = if (type == NotificationType.Advertencia) Color.Black else iconColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = description,
                    color = if (type == NotificationType.Advertencia) Color.Black else iconColor,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
