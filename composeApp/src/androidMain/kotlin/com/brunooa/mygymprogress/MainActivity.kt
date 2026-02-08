package com.brunooa.mygymprogress

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.brunooa.mygymprogress.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        initKoin()

        setContent {
            App()
        }
    }
}

fun getApplicationContext(): Context {
    val application = (MainActivity::class.java.classLoader?.loadClass("android.app.ActivityThread")
        ?.getMethod("currentApplication")?.invoke(null) as? android.app.Application)
    return application?.applicationContext
        ?: throw IllegalStateException("Application context not available")
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}