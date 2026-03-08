package com.aarondeveloper.supredatard.librery

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun GifImage(
    modifier: Modifier,
    url: String?,
    drawable: String?,
    scaleMode: ContentScale
) {
    val context = LocalContext.current
    val imageLoader = getGifImageLoader(context)

    when {
        url != null -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = scaleMode,
                modifier = modifier,
                imageLoader = imageLoader
            )
        }
        drawable != null -> {
            val resId = context.resources.getIdentifier(drawable, "drawable", context.packageName)
            if (resId != 0) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(resId)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = scaleMode,
                    modifier = modifier,
                    imageLoader = imageLoader
                )
            }
        }
    }
}

@Composable
actual fun ImagenUrl(
    modifier: Modifier,
    url: String?,
    scaleMode: ContentScale
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = scaleMode,
        modifier = modifier
    )
}

fun getGifImageLoader(context: Context) = ImageLoader.Builder(context)
    .components {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }
    .build()

actual fun logDebug(message: String) {
    android.util.Log.d("Informacion: ", message)
}


actual fun convertirBase64AImageBitmap(base64String: String): ImageBitmap? {
    return try {
        val cleanBase64 = base64String
            .substringAfter("base64,", base64String)
            .trim()

        val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

actual fun getYear(): Int {
    val calendar = java.util.Calendar.getInstance()
    return calendar.get(java.util.Calendar.YEAR)
}