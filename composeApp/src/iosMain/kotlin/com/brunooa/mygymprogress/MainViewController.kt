package com.brunooa.mygymprogress

import androidx.compose.ui.window.ComposeUIViewController
import com.brunooa.mygymprogress.di.initKoin

fun MainViewController() {
    initKoin()
    return ComposeUIViewController { App() }
}