package com.gyub.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gyub.core.design.theme.MovieFinderTheme
import com.gyub.feature.main.navigator.rememberMainNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MovieFinderTheme {
                MainScreen(rememberMainNavigator())
            }
        }
    }
}