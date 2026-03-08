package com.aarondeveloper.supredatard.presentation.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart

@Composable
fun Quantity(
    cantidad: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    buttonColor: Color,
    borderColor: Color,
    textColor: Color,
    minCantidad: Int,
    maxCantidad: Int
) {
    if (maxCantidad == 0) {
        Text(
            text = "Agotado",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MoradoStart,
            modifier = modifier
        )
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { if (cantidad > minCantidad) onDecrement() },
                modifier = Modifier
                    .size(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                border = BorderStroke(1.dp, borderColor),
                shape = shape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("-", fontSize = 16.sp, color = textColor)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "$cantidad",
                fontSize = 14.sp,
                color = textColor,
                modifier = Modifier
                    .width(40.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { if (cantidad < maxCantidad) onIncrement() },
                modifier = Modifier
                    .size(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                border = BorderStroke(1.dp, borderColor),
                shape = shape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("+", fontSize = 16.sp, color = textColor)
            }
        }
    }
}