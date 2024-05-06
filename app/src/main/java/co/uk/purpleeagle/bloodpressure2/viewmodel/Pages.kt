package co.uk.purpleeagle.bloodpressure2.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import co.uk.purpleeagle.bloodpressure2.model.csv.Csv
import co.uk.purpleeagle.bloodpressure2.navigationWrapper
import co.uk.purpleeagle.bloodpressure2.view.screens.*

val navPages = listOf(
    Pages.LIST,
    Pages.CSV,
    Pages.GRAPH
)

enum class Pages(
    val route: String,
    val label: String,
    val imageVector: ImageVector,
    val onNavigate: suspend () -> Unit,
    val screen: @Composable (UiState, ModelState, (Event)->Unit) -> Unit
) {
    LIST(
        route = "home",
        onNavigate = {},
        label = "home",
        imageVector = Icons.Rounded.Home,
        screen = { uiState, modelState, onEvent ->
            ListScreen(
                uiState = uiState,
                modelState = modelState,
                onEvent = onEvent
            )
        }
    ),
    EDIT(
        route = "edit",
        onNavigate = {

        },
        label = "Edit",
        imageVector = Icons.Rounded.Edit,
        screen = { uiState, modelState, onEvent ->
            EditScreen(
                uiState = uiState,
                modelState = modelState,
                onEvent = onEvent
            )
        }
    ),
    CSV(
        route = "csv",
        label = "CSV",
        imageVector = Icons.Rounded.FileDownload,
        onNavigate = {
            if (!navigationWrapper.modelState.hasDirectory)
                navigationWrapper.navigate(DIRECTORY)
        },
        screen = { uiState, modelState, onEvent ->
            CsvScreen(
                uiState = uiState,
                modelState = modelState,
                onEvent = onEvent
            )
        }
    ),
    DIRECTORY(
        route = "dir",
        label = "Directory",
        imageVector = Icons.Rounded.Folder,
        onNavigate = {

        },
        screen = { uiState, modelState, onEvent ->
            DirectoryScreen(
                uiState = uiState,
                modelState = modelState,
                onEvent = onEvent
            )
        }
    ),
    GRAPH(
        route = "graphs",
        label = "Graphs",
        imageVector = Icons.Filled.TrendingUp,
        onNavigate = {

        },
        screen = { uiState, modelState, onEvent ->
            GraphScreen(
                uiState = uiState,
                modelState = modelState,
                onEvent = onEvent
            )
        }
    )
}


