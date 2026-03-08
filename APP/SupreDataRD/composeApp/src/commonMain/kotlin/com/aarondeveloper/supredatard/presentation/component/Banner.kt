package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

// 1️⃣ Función que devuelve la representación ASCII de cada letra
fun letra(caracter: Char): String {
    return when (caracter.uppercaseChar()) {
        'A' -> """
            ╔█████═╗
            ██    ██║
            ██    ██║
            ██    ██║
            ████████║
            ██╔═══██╗
            ██║   ██║
            ╚═╝   ╚═╝
        """.trimIndent()
        'B' -> """
            ██████╗
            ██╔══██╗
            ██║  ██╗
            ██████╔╝
            ██╔══██╗
            ██║  ██║
            ██████╔╝
            ╚═════╝
        """.trimIndent()
        'C' -> """
             ██████╗ 
            ██╔════╝
            ██║
            ██║
            ██║
            ██║
            ╚██████╗
             ╚═════╝
        """.trimIndent()
        'D' -> """
            ███████╗  
            ██╔════██╗ 
            ██║    ██║ 
            ██║    ██║ 
            ██║    ██║ 
            ██╔════██╗ 
            ████████╔╝ 
            ╚═══════╝  
        """.trimIndent()
        'E' -> """
            ███████╗ 
            ██╔════╝
            ██║ 
            █████╗   
            ██╔══╝   
            ██║      
            ███████╗ 
            ╚══════╝     
        """.trimIndent()
        'F' -> """
            ████████╗ 
            ██╔═════╝ 
            █████╗   
            ██╔══╝   
            ██║ 
            ██║     
            ██║
            ╚═╝     
        """.trimIndent()
        'G' -> """
             ███████╗ 
            ██╔════╝ 
            ██║      
            ██║  ███╗
            ██║   ██║
            ██║   ██║
            ╚██████╔╝
             ╚═════╝ 
        """.trimIndent()
        'H' -> """
            ██╗  ██╗ 
            ██║  ██║ 
            ██║  ██║ 
            ███████║ 
            ██╔══██║ 
            ██║  ██║ 
            ██║  ██║ 
            ╚═╝  ╚═╝ 
        """.trimIndent()
        'I' -> """
            ██████████╗
                ██╔═══╝   
                ██║  
                ██║ 
                ██║   
                ██╚═══╗   
            ██████████║
            ╚═════════╝
        """.trimIndent()
        'J' -> """
            .    ██╗
                 ██║
                 ██║
                 ██║
            ██   ██║
            ██   ██║
             ██████║
              ╚════╝
        """.trimIndent()
        'K' -> """
            ██╗  ██╗
            ██║ ██╔╝
            ██║██╔╝ 
            ████╔╝  
            ██║██╗  
            ██║ ╚██╗
            ██║  ╚██╗
            ╚═╝   ╚═╝
        """.trimIndent()
        'L' -> """
            ██╗
            ██║
            ██║
            ██║
            ██║
            ██║
            ███████╗
            ╚══════╝
        """.trimIndent()
        'M' -> """
            ███╗   ███╗
            ████╗ ████║
            ██╔████╔██║
            ██║╚██╔╝██║
            ██║ ╚═╝ ██║
            ██║     ██║
            ██║     ██║
            ╚═╝     ╚═╝
        """.trimIndent()
        'N' -> """
            ███╗   ██╗
            ████╗  ██║
            ██╔██╗ ██║
            ██╔██╗ ██║
            ██║╚██╗██║
            ██║ ╚████║
            ██║  ╚███║
            ╚═╝   ╚══╝
        """.trimIndent()
        'O' -> """
             ██████╗ 
            ██╔═══██╗
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ╚██████╔╝
             ╚═════╝ 
        """.trimIndent()
        'P' -> """
            ███████╗
            ██╔══██╗
            ██████╔╝
            ██╔═══╝
            ██║
            ██║
            ██║
            ╚═╝
        """.trimIndent()
        'Q' -> """
             ██████╗   
            ██╔═══██╗  
            ██║   ██║  
            ██║   ██║  
            ██║    █║  
            ╚████████═╗
             ╚══════▀▀╝
        """.trimIndent()
        'R' -> """
            ███████╗  
            ██   ██╗
            ██   ██╗  
            ██████╔╝ 
            ██╔═══╝  
            ██║  ██╗
            ██║  ██║
            ╚═╝  ╚═╝
        """.trimIndent()
        'S' -> """
            █████████╗
            ██╔══════╝
              ╚╗ 
               ╚██████╗
                    ██╗
                    ██╗
            ████████╔╝
            ╚═══════╝
        """.trimIndent()
        'T' -> """
            ████████╗
            ╚══██╔══╝
               ██║   
               ██║
               ██║   
               ██║   
               ██║   
               ╚═╝ 
        """.trimIndent()
        'U' -> """
            ██╗   ██╗
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ╚██████╔╝
             ╚═════╝
        """.trimIndent()
        'V' -> """
            ██╗   ██╗
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ██║   ██║
            ╚██╗ ██╔╝
             ╚████╔╝ 
              ╚═══╝   
        """.trimIndent()
        'W' -> """
            ██╗    ██╗
            ██║    ██║
            ██║    ██║
            ██║    ██║
            ██║ ██╗██║
            ██║███╗██║
            ╚███╔███╔╝
             ╚══╝╚══╝   
        """.trimIndent()
        'X' -> """
            ██╗  ██╗
            ╚██╗██╔╝
             ╚███╔╝ 
              ╚██╔╝ 
              ║██║ 
              ███║  
             ██╔██╗ 
            ██╔╝ ╚██╗
            ╚═╝   ╚═╝
        """.trimIndent()
        'Y' -> """
            ██╗   ██╗
            ╚██╗ ██╔╝
            ╚██╗ ██╔╝
             ╚████╔╝ 
              ╚██╔╝  
               ██║   
               ██║   
               ╚═╝   
        """.trimIndent()
        'Z' -> """
            █████████╗
            ╚═════██╔╝
                ██╔╝   
               ██╔╝
              ██╔╝    
             ██╔╝     
            ██╔════╗   
            ████████╗   
        """.trimIndent()
        '&' -> """
             ██████╗     
            ██╔═══██╗    
            ╚═╝   ██║    
             ██████╔╝    
            ██╔═══██╗    
            ██║  ██║██╗  
            ╚██████╔╝╚═╝ 
             ╚═════╝     
        """.trimIndent()
        else -> caracter.toString()
    }
}

fun getRandomColor(): String {
    val colors = listOf(
        "#FF6B6B", "#FFD93D", "#6BCB77", "#4D96FF",
        "#FF9CEE", "#FFB26B", "#A66BFF", "#6FFFE9",
        "#FFC6FF", "#5C7AEA"
    )
    return colors.random()
}

fun hexToColor(hex: String): Color {
    val cleanHex = hex.removePrefix("#")
    val colorLong = cleanHex.toLong(16)
    return when (cleanHex.length) {
        6 -> Color(
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f,
            alpha = 1f
        )
        8 -> Color(
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f,
            alpha = ((colorLong shr 24) and 0xFF) / 255f
        )
        else -> Color.White
    }
}

@Composable
fun AsciiText(
    texto: String,
    fontSize: TextUnit = 16.sp,
    spacing: Dp = 4.dp,
    background: Color = Color.Black,
    alignment: String = "central"
) {
    val letras = texto.map { letra(it).trim().lines().map { it.trimEnd() } }
    val maxFilas = letras.maxOf { it.size }

    val letrasCompletas = letras.map { lineas ->
        val lineasCompletas = lineas.toMutableList()
        while (lineasCompletas.size < maxFilas) {
            lineasCompletas.add(" ".repeat(lineas.firstOrNull()?.length ?: 0))
        }
        lineasCompletas
    }

    val colores = letrasCompletas.map { hexToColor(getRandomColor()) }

    val rowAlignment = when (alignment.lowercase()) {
        "izquierda" -> Arrangement.Start
        "derecha" -> Arrangement.End
        else -> Arrangement.Center
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(8.dp),
        contentAlignment = when (alignment.lowercase()) {
            "izquierda" -> Alignment.CenterStart
            "derecha" -> Alignment.CenterEnd
            else -> Alignment.Center
        }
    ) {
        Row {
            letrasCompletas.forEachIndexed { index, letraLineas ->
                Column(
                    modifier = Modifier.padding(end = spacing)
                ) {
                    letraLineas.forEach { linea ->
                        Text(
                            text = linea,
                            fontFamily = FontFamily.Monospace,
                            fontSize = fontSize,
                            color = colores[index]
                        )
                    }
                }
            }
        }
    }
}


