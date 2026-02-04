package com.ivan.version6pro

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ivan.version6pro.ui.theme.IvanVersion6ProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable Edge-to-Edge to draw behind bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            IvanVersion6ProTheme {
                // Set up Navigation Bar styling per requirements
                SystemBarConfig()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(), // Ensure content stays above keyboard
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // WebView Placeholder for the converted Node.js app
                        // In a real scenario, assets would be in src/main/assets/www
                        WebContentView()
                    }
                }
            }
        }
    }
}

@Composable
fun SystemBarConfig() {
    val view = LocalView.current
    val isDark = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            // Requirement: Navigation bar background MUST match app background
            window.navigationBarColor = backgroundColor.toArgb()
            window.statusBarColor = backgroundColor.toArgb()

            // Requirement: Icons MUST be BLACK if background is light (isDark=false), 
            // and WHITE if background is dark (isDark=true)
            insetsController.isAppearanceLightNavigationBars = !isDark
            insetsController.isAppearanceLightStatusBars = !isDark
        }
    }
}

@Composable
fun WebContentView() {
    // A view to render the "Node.js" web content
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(), // Avoid overlap with system bars if not using Scaffold padding
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                
                // Load a generic placeholder or local assets
                // For a "converted" node app, usually we start local server or load assets:
                // loadUrl("file:///android_asset/index.html")
                loadUrl("https://google.com") // Placeholder for build demonstration
            }
        }
    )
}