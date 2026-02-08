package com.brunooa.mygymprogress

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.brunooa.mygymprogress.theme.MyGymProgressTheme
import com.brunooa.mygymprogress.ui.home.HomeScreen

@Composable
fun App() {
    MyGymProgressTheme {
        Navigator(HomeScreen())
    }
}