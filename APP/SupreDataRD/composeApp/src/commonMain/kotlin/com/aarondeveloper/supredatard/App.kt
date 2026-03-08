package com.aarondeveloper.supredatard

import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.aarondeveloper.supredatard.presentation.component.NotificationHost
import com.aarondeveloper.supredatard.presentation.navigation.NavigationNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {

    KoinContext {
        val navHost = rememberNavController()
        NavigationNavHost(navHost)
        NotificationHost()
    }

}