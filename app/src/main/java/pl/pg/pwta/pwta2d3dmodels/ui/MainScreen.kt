package pl.pg.pwta.pwta2d3dmodels.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.pg.pwta.pwta2d3dmodels.model.ModelInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.platform.LocalContext
import pl.pg.pwta.pwta2d3dmodels.model.ModelFormat
import pl.pg.pwta.pwta2d3dmodels.util.readModelInfoFromUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedModel by remember { mutableStateOf<ModelInfo?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedModel = readModelInfoFromUri(context, uri)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("2D / 3D Model Viewer") },
                actions = {
                    IconButton(
                        onClick = { showInfoDialog = true },
                        enabled = selectedModel != null
                    ) {
                        Icon(
                            Icons.Rounded.Info,
                            contentDescription = "Informacje o pliku"
                        )
                    }
                    IconButton(
                        onClick = {
                            openFileLauncher.launch(arrayOf("*/*"))
                        }
                    ) {
                        Icon(
                            Icons.Rounded.AddCircle,
                            contentDescription = "Otwórz plik"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            RenderSurface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                model = selectedModel
            )
            StatusBar(model = selectedModel)
        }
    }

    if (showInfoDialog && selectedModel != null) {
        ModelInfoDialog(
            model = selectedModel!!,
            onDismiss = { showInfoDialog = false }
        )
    }
}

@Composable
fun RenderSurface(
    modifier: Modifier,
    model: ModelInfo?
) {
    fun ModelFormat.isRenderable3D(): Boolean =
        this in setOf(
            ModelFormat.OBJ,
            ModelFormat.GLTF,
            ModelFormat.GLB,
            ModelFormat.STL
        )
    Surface(modifier = modifier) {
        when {

            else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nie załadowano modelu")
            }
//            model == null -> {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Text("Nie załadowano modelu")
//                }
//            }
//
//            model.format in setOf(ModelFormat.GLTF, ModelFormat.GLB, ModelFormat.OBJ) -> {
//                Render3DSceneView(model)
//            }
//
//            model.format == ModelFormat.STL -> {
//                RenderSTL(model)
//            }
//
//            model.format.isRenderable2D() -> {
//                Render2D(model)
//            }
//
//            else -> {
//                UnsupportedFormatView(model.format)
//            }
        }
    }
}

@Composable
fun OpenModelDialog(
    onDismiss: () -> Unit,
    onModelSelected: (ModelInfo) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Wybierz model") },
        text = {
            // TODO:
            // - lista modeli z assets
            // - lub picker systemowy (SAF)
            Text("Lista predefiniowanych modeli (TODO)")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Zamknij")
            }
        }
    )
}

@Composable
fun ModelInfoDialog(
    model: ModelInfo,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Informacje o pliku") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Nazwa pliku: ${model.fileName}")
                Text("Ścieżka / URI: ${model.filePath}")
                Text("Rozszerzenie: ${model.extension}")
                Text("Format: ${model.format}")
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text("Rozmiar: ${model.fileSizeBytes} B")
                Text(
                    "Typ MIME: ${
                        model.mimeType ?: "brak danych"
                    }"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text(
                    "Data utworzenia: ${
                        model.createdAt?.toString() ?: "brak danych"
                    }"
                )
                Text(
                    "Data modyfikacji: ${
                        model.modifiedAt?.toString() ?: "brak danych"
                    }"
                )
                Text(
                    "Ostatni dostęp: ${
                        model.accessedAt?.toString() ?: "brak danych"
                    }"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                Text("Źródło: ${if (model.isEmbedded) "assets aplikacji" else "zewnętrzny plik"}")
                Text(
                    "Uprawnienia: " +
                            "${if (model.isReadable) "R" else "-"}" +
                            "${if (model.isWritable) "W" else "-"}"
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun StatusBar(model: ModelInfo?) {
    Surface(tonalElevation = 2.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = model?.fileName ?: "Brak załadowanego modelu",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}