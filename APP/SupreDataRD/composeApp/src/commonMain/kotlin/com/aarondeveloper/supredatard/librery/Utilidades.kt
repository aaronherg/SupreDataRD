package com.aarondeveloper.supredatard.librery

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource

val APIKEY = "admin_ranger_aaron"
val SMTPHOST  = "smtp.gmail.com"
val SMTPUSERNAME = "atminifg655@gmail.com"
val SMTPPASSWORD = "nnloajppyuaipigb"
val SMTPPORT = "587"

@Composable
expect fun GifImage(
    modifier: Modifier = Modifier,
    url: String? = null,
    drawable: String? = null,
    scaleMode: ContentScale = ContentScale.Fit
)

@Composable
expect fun ImagenUrl(
    modifier: Modifier = Modifier,
    url: String? = null,
    scaleMode: ContentScale = ContentScale.Fit
)

expect fun logDebug(message: String)

expect fun convertirBase64AImageBitmap(base64String: String): ImageBitmap?

expect fun getYear(): Int