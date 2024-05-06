package co.uk.purpleeagle.bloodpressure2.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.uk.purpleeagle.bloodpressure2.view.components.BottomNavBar
import co.uk.purpleeagle.bloodpressure2.view.components.SelectionDropdown
import co.uk.purpleeagle.bloodpressure2.view.components.graph.Graph
import co.uk.purpleeagle.bloodpressure2.view.components.rmw
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState

@Composable
fun GraphScreen(
    uiState: UiState,
    modelState: ModelState,
    onEvent: (Event) -> Unit
) {
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
    ) {padding -> padding.rmw
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectionDropdown(
                    nexi = true,
                    uiState = uiState,
                    modelState = modelState,
                    onEvent = onEvent
                )
                if (modelState.currentRecords.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(0.8f)
                    ) {
                        Graph(records = modelState.currentRecords)
                    }
                }else{
                    Text(text = "Add some records")
                }
            }
        }
    }
}