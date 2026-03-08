package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.aarondeveloper.supredatard.librery.GifImage
import com.aarondeveloper.supredatard.librery.getYear
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.jetbrains.compose.resources.painterResource
import supredatard.composeapp.generated.resources.Res
import supredatard.composeapp.generated.resources.calendar


@Composable
fun Calendar(
    label: String,
    selectedDate: String,
    onDateChange: (String) -> Unit,
    borderRadius: Int,
    borderColor: Brush,
    textSize: Int,
    verticalPadding: Int,
    modifier: Modifier = Modifier
) {
    var isPickerOpen by remember { mutableStateOf(false) }

    val monthsText = listOf(
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    )

    val currentYear = getCurrentYear()
    var selectedYear by remember { mutableStateOf(selectedDate.split("-").getOrNull(0)?.toIntOrNull() ?: currentYear) }
    var selectedMonth by remember { mutableStateOf(selectedDate.split("-").getOrNull(1)?.toIntOrNull() ?: 1) }
    var selectedDay by remember { mutableStateOf(selectedDate.split("-").getOrNull(2)?.toIntOrNull() ?: 1) }

    val years = (1900..currentYear).toList()

    // ----------------- TRIGGER -----------------
    Box(
        modifier = modifier
            .border(width = 2.dp, brush = borderColor, shape = RoundedCornerShape(borderRadius.dp))
            .clickable { isPickerOpen = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = verticalPadding.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedDate.isNotEmpty() && !isPickerOpen) {
                    "${selectedYear.toString().padStart(4, '0')}-${selectedMonth.toString().padStart(2, '0')}-${selectedDay.toString().padStart(2, '0')}"
                } else label,
                fontSize = textSize.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = painterResource(Res.drawable.calendar),
                contentDescription = "Calendario",
                modifier = Modifier.size(20.dp)
            )
        }
    }

    // ----------------- MODAL FLOTANTE -----------------
    if (isPickerOpen) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { isPickerOpen = false }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    GifImage(
                        modifier = Modifier
                            .matchParentSize(),
                        drawable = "gif_dinosaurio",
                        scaleMode = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(0xFF0A0A0C).copy(alpha = 0.85f))
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
                                text = "terminal — Calendar",
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
                                .padding(horizontal = 16.dp, vertical = 5.dp)
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                        ) {
                            AsciiText(
                                texto = "CALENDAR",
                                fontSize = 2.sp,
                                spacing = 8.dp,
                                background = Color.Transparent,
                                alignment = "central"
                            )

                            Text(
                                text = label,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp
                            )

                            Spacer(Modifier.height(8.dp))

                            Column(modifier = Modifier.fillMaxWidth()) {

                                Spacer(Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text("Día", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                                    Text("Mes", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                                    Text("Año", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                                }

                                Spacer(Modifier.height(8.dp))

                                val daysInMonth = getDaysInMonth(selectedYear, selectedMonth)
                                val days = (1..daysInMonth).toList()

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    WheelSelector(items = days, selectedItem = selectedDay, onItemSelected = { selectedDay = it })
                                    WheelSelector(items = monthsText, selectedItem = monthsText[selectedMonth - 1], onItemSelected = { selectedMonth = monthsText.indexOf(it) + 1 })
                                    WheelSelector(items = years.reversed(), selectedItem = selectedYear, onItemSelected = { selectedYear = it })
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Listo",
                                        color = Color(0xFF007AFF),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .clickable {
                                                onDateChange(
                                                    "$selectedYear-${selectedMonth.toString().padStart(2,'0')}-${selectedDay.toString().padStart(2,'0')}"
                                                )
                                                isPickerOpen = false
                                            }
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }

                                Spacer(Modifier.height(8.dp))
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> WheelSelector(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit
) {
    val listState = rememberLazyListState(items.indexOf(selectedItem))

    LazyColumn(
        state = listState,
        modifier = Modifier
            .width(80.dp)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemSelected(item) }
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.toString(),
                    fontSize = 12.sp,
                    color = if (item == selectedItem) Color(0xFF007AFF) else Color.Gray
                )
            }
        }
    }
}

fun getDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1,3,5,7,8,10,12 -> 31
        4,6,9,11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 30
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun getCurrentYear(): Int {
    return getYear()
}