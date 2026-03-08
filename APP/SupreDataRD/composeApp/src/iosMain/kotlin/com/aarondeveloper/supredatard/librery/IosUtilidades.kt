package com.aarondeveloper.supredatard.librery

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import platform.CoreFoundation.CFDataRef
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGContextDrawImage
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.kCGBitmapByteOrder32Little
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSDictionary
import platform.Foundation.NSNumber
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.dataWithContentsOfURL
import platform.ImageIO.CGImageSourceCopyPropertiesAtIndex
import platform.ImageIO.CGImageSourceCreateImageAtIndex
import platform.ImageIO.CGImageSourceCreateWithData
import platform.ImageIO.CGImageSourceGetCount
import platform.ImageIO.kCGImagePropertyGIFDelayTime
import platform.ImageIO.kCGImagePropertyGIFDictionary
import platform.ImageIO.kCGImagePropertyGIFUnclampedDelayTime
import platform.UIKit.UIImage
import platform.posix.memcpy
import kotlin.time.ExperimentalTime

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
@Composable
actual fun GifImage(
    modifier: Modifier,
    url: String?,
    drawable: String?,
    scaleMode: ContentScale
) {

    var frames by remember { mutableStateOf<List<ImageBitmap>>(emptyList()) }
    var frameDelays by remember { mutableStateOf<List<Long>>(emptyList()) }
    var frameIndex by remember { mutableStateOf(0) }

    LaunchedEffect(url, drawable) {

        val data: NSData? = when {
            url != null -> {
                val nsUrl = NSURL(string = url)
                NSData.dataWithContentsOfURL(nsUrl)
            }
            drawable != null -> {
                val path = NSBundle.mainBundle.pathForResource(drawable, "gif")
                NSData.dataWithContentsOfFile(path!!)
            }
            else -> null
        }

        data ?: return@LaunchedEffect

        val dataRef = CFBridgingRetain(data) as CFDataRef
        val source = CGImageSourceCreateWithData(dataRef, null) ?: return@LaunchedEffect
        val count = CGImageSourceGetCount(source).toInt()

        val frameList = mutableListOf<ImageBitmap>()
        val delays = mutableListOf<Long>()

        for (i in 0 until count) {

            val cgImage = CGImageSourceCreateImageAtIndex(source, i.toULong(), null) ?: continue

            val width = CGImageGetWidth(cgImage).toInt()
            val height = CGImageGetHeight(cgImage).toInt()

            val bytesPerPixel = 4
            val bytesPerRow = bytesPerPixel * width

            val pixelData = ByteArray(bytesPerRow * height)

            val colorSpace = CGColorSpaceCreateDeviceRGB()

            memScoped {

                val bitmapInfo =
                    CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value or
                            kCGBitmapByteOrder32Little

                val context = CGBitmapContextCreate(
                    pixelData.refTo(0),
                    width.toULong(),
                    height.toULong(),
                    8u,
                    bytesPerRow.toULong(),
                    colorSpace,
                    bitmapInfo
                )

                CGContextDrawImage(
                    context,
                    CGRectMake(0.0, 0.0, width.toDouble(), height.toDouble()),
                    cgImage
                )
            }

            val skiaImage = Image.makeRaster(
                ImageInfo.makeN32Premul(width, height),
                pixelData,
                bytesPerRow
            )

            frameList.add(skiaImage.toComposeImageBitmap())

            val properties = CGImageSourceCopyPropertiesAtIndex(source, i.toULong(), null)
            val dict = properties as? NSDictionary

            val gifDict = dict?.objectForKey(kCGImagePropertyGIFDictionary) as? NSDictionary

            val delay =
                (gifDict?.objectForKey(kCGImagePropertyGIFUnclampedDelayTime) as? NSNumber)?.doubleValue
                    ?: (gifDict?.objectForKey(kCGImagePropertyGIFDelayTime) as? NSNumber)?.doubleValue
                    ?: 0.04

            delays.add((delay * 1000).toLong())
        }

        frames = frameList
        frameDelays = delays
    }

    LaunchedEffect(frames, frameDelays) {

        while (true) {

            if (frames.isNotEmpty()) {

                val delayTime = frameDelays.getOrNull(frameIndex) ?: 16

                delay(delayTime)

                frameIndex = (frameIndex + 1) % frames.size
            } else {
                delay(16)
            }
        }
    }

    if (frames.isNotEmpty()) {

        Image(
            bitmap = frames[frameIndex],
            contentDescription = null,
            modifier = modifier,
            contentScale = scaleMode
        )
    }
}


@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
@Composable
actual fun ImagenUrl(
    modifier: Modifier,
    url: String?,
    scaleMode: ContentScale
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        if (url.isNullOrEmpty()) return@LaunchedEffect

        val nsUrl = NSURL(string = url)
        val data = NSData.dataWithContentsOfURL(nsUrl) ?: return@LaunchedEffect

        val uiImage = UIImage(data = data)

        val cgImage = uiImage.CGImage ?: return@LaunchedEffect
        val width = CGImageGetWidth(cgImage).toInt()
        val height = CGImageGetHeight(cgImage).toInt()
        val bytesPerPixel = 4
        val bytesPerRow = bytesPerPixel * width
        val pixelData = ByteArray(bytesPerRow * height)
        val colorSpace = CGColorSpaceCreateDeviceRGB()

        memScoped {
            val context = CGBitmapContextCreate(
                pixelData.refTo(0),
                width.toULong(),
                height.toULong(),
                8u,
                bytesPerRow.toULong(),
                colorSpace,
                CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value or
                        kCGBitmapByteOrder32Little
            )

            CGContextDrawImage(
                context,
                CGRectMake(0.0, 0.0, width.toDouble(), height.toDouble()),
                cgImage
            )
        }

        val skiaImage = Image.makeRaster(
            ImageInfo.makeN32Premul(width, height),
            pixelData,
            bytesPerRow
        )

        imageBitmap = skiaImage.toComposeImageBitmap()
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = modifier,
            contentScale = scaleMode
        )
    }
}

actual fun logDebug(message: String) {
    println(message)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun convertirBase64AImageBitmap(base64String: String): ImageBitmap? {
    return try {
        val cleanBase64 = base64String
            .substringAfter("base64,", base64String)
            .trim()

        val data = NSData.create(
            base64EncodedString = cleanBase64,
            options = 0u
        ) ?: return null

        val bytes = ByteArray(data.length.toInt())
        memcpy(bytes.refTo(0), data.bytes, data.length)

        val skiaImage = Image.makeFromEncoded(bytes)
        skiaImage.toComposeImageBitmap()

    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@OptIn(ExperimentalTime::class)
actual fun getYear(): Int {

    return 0
}