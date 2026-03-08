package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart

@Composable
fun DatosInfoRow(
    label: String,
    value: String,
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(horizontalAlignment = alignment) {
        Text(
            text = label,
            fontSize = 10.sp,
            style = TextStyle(Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)))
        )
        SelectionContainer {
            Text(
                text = value,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}
