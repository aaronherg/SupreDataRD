package com.aarondeveloper.supredatard.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aarondeveloper.dealerpos.ui.theme.MarronEnd
import org.aarondeveloper.dealerpos.ui.theme.MoradoStart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class NavItem(
    val name: String,
    val iconRes: DrawableResource,
    val onClick: () -> Unit = {}
)

@Composable
fun NavigationBar(
    items: List<NavItem>,
    initialSelectedItem: String = items.firstOrNull()?.name ?: "",
    onItemSelected: (String) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf(initialSelectedItem) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(16.dp, bottom = 3.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(35.dp))
            .border(2.dp, Brush.horizontalGradient(listOf(MoradoStart, MarronEnd)), RoundedCornerShape(50.dp)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            items.forEach { item ->
                NavBarItem(
                    name = item.name,
                    iconRes = item.iconRes,
                    isSelected = selectedItem == item.name,
                    onClick = {
                        selectedItem = item.name
                        onItemSelected(item.name)
                        item.onClick()
                    }
                )
            }
        }
    }
}

@Composable
fun NavBarItem(
    name: String,
    iconRes: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(resource = iconRes),
            contentDescription = name,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) Color.White else Color.White
        )

        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

