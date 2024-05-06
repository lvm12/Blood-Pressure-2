package co.uk.purpleeagle.bloodpressure2.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordEditSource
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import co.uk.purpleeagle.bloodpressure2.view.components.BottomNavBar
import co.uk.purpleeagle.bloodpressure2.view.components.SelectionDropdown
import co.uk.purpleeagle.bloodpressure2.view.components.list.RecordBottomSheet
import co.uk.purpleeagle.bloodpressure2.view.components.list.RecordItem
import co.uk.purpleeagle.bloodpressure2.view.components.rmw
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState

@Composable
fun ListScreen(
    uiState: UiState,
    modelState: ModelState,
    onEvent: (Event) -> Unit
) {
    Scaffold(
        topBar = {
            SelectionDropdown(
                nexi = false,
                uiState = uiState,
                modelState = modelState,
                modifier = Modifier
                    .height(60.dp)
                    .width(150.dp)
                ,
                onEvent = onEvent
            )
        },
        bottomBar = {
            BottomNavBar(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                onEvent = onEvent
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = { onEvent(Event.GoToEditScreen(source = RecordEditSource.NEW)) },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20)
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add record",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    ) {padding -> padding.rmw
        Box (modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier
                    .height(80.dp)
                )
                Text(
                    text = "Records (${modelState.currentRecords.size}):",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                RecordItem(
                    record = Record(
                        id = 0L,
                        systolicPressure = "SYS",
                        diastolicPressure = "DIA",
                        pulse = "PUL",
                        comment = "COM",
                        status = RecordStatus.NEW,
                        createdAt = 0L
                    ),
                    usesDate = false,
                    usesComment = false,
                    onEvent = onEvent
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(modifier = Modifier.fillMaxWidth(0.9f))
                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(modelState.currentRecords) { record ->
                        RecordItem(
                            record = record,
                            onEvent = onEvent
                        )
                    }
                }
            }
            if (uiState.showBottomSheet) {
                RecordBottomSheet(
                    record = modelState.currentRecord,
                    onEvent = onEvent
                )
            }
        }
    }
}