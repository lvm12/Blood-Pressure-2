package co.uk.purpleeagle.bloodpressure2.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import co.uk.purpleeagle.bloodpressure2.view.components.BottomNavBar
import co.uk.purpleeagle.bloodpressure2.view.components.SelectionDropdown
import co.uk.purpleeagle.bloodpressure2.view.components.rmw
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.Pages
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState

@Composable
fun CsvScreen(
    uiState: UiState,
    modelState: ModelState,
    onEvent: (Event) -> Unit
) {
    if (uiState.showCsvToast){
        Toast(LocalContext.current).apply {
            setText("CSV generated")
            duration = Toast.LENGTH_SHORT
        }.show()
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                onEvent = onEvent
            )
        }
    ) { padding ->
        padding.rmw
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = {
                    onEvent(Event.Navigate(Pages.DIRECTORY))
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Change file path"
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SelectionDropdown(
                    nexi = false,
                    uiState = uiState,
                    modelState = modelState,
                    onEvent = onEvent
                )
                TextField(
                    value = uiState.csvFileName,
                    onValueChange = {
                        onEvent(Event.CsvFileName(it))
                    },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    placeholder = {
                        Text(text = "Enter filename")
                    }
                )
                Button(
                    onClick = {
                        onEvent(Event.GenerateCSV)
                    }
                ) {
                    Text(text = "Generate CSV")
                }
            }
        }
    }
}