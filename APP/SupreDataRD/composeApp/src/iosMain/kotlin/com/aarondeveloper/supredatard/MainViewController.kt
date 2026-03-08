package com.aarondeveloper.supredatard

import androidx.compose.ui.window.ComposeUIViewController
import com.aarondeveloper.supredatard.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
)
{
    App()
}