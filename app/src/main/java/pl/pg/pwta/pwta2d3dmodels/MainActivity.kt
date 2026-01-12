package pl.pg.pwta.pwta2d3dmodels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import pl.pg.pwta.pwta2d3dmodels.ui.MainScreen
import pl.pg.pwta.pwta2d3dmodels.ui.theme.PwTA2D3DModelsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PwTA2D3DModelsTheme {
                MaterialTheme {
                    MainScreen()
                }
            }
        }
    }
}