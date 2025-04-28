package com.mantisbayne.leetcodecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mantisbayne.leetcodecompose.screen.SolutionScreen
import com.mantisbayne.leetcodecompose.ui.theme.LeetcodeComposeTheme
import com.mantisbayne.leetcodecompose.viewmodel.SolutionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeetcodeComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SolutionScreen(
                        Modifier.padding(innerPadding),
                        hiltViewModel<SolutionViewModel>()
                    )
                }
            }
        }
    }
}
