package com.emirhankarci.tutorly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.emirhankarci.tutorly.presentation.navigation.Navigation
// import com.emirhankarci.tutorly.presentation.ui.screen.GeminiScreen
import com.emirhankarci.tutorly.presentation.ui.theme.TutorlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutorlyTheme {
                Navigation()

                // GeminiScreen - Commented out as requested
                /*
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GeminiScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                */
            }
        }
    }
}