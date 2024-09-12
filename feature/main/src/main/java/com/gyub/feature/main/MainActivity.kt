package com.gyub.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gyub.feature.main.navigator.rememberMainNavigator
import dagger.hilt.android.AndroidEntryPoint
import net.skyscanner.backpack.compose.theme.BpkTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BpkTheme {
                MainScreen(rememberMainNavigator())
            }
        }
    }
}