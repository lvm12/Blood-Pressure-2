package co.uk.purpleeagle.bloodpressure2.view.components.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordEditSource
import co.uk.purpleeagle.bloodpressure2.model.record.emptyRecord
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordBottomSheet(
    record: Record,
    onEvent: (Event) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(Event.ManageBottomSheet(
                show = false,
                record = emptyRecord()
            ))
        }
    ) {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = {
                    onEvent(
                        Event.ManageBottomSheet(
                            show = false,
                            record = emptyRecord()
                        )
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close bottom sheet"
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "SYS: ${record.systolicPressure}")
                Text(text = "DIA: ${record.diastolicPressure}")
                Text(text = "PUL: ${record.pulse}")
                Text(text = "DATE: ${record.createdAt.toDate()}")
                if (record.comment.isNotBlank()) {
                    Text(text = "COM: ${record.comment}")
                }
                Row {
                    Button(
                        onClick = {
                            onEvent(Event.DeleteRecord(record))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DeleteForever,
                            contentDescription = null
                        )
                        Text(text = "Delete record")
                    }
                    Button(
                        onClick = {
                            onEvent(
                                Event.GoToEditScreen(
                                    source = RecordEditSource.EDIT,
                                    record = record
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null
                        )
                        Text(text = "Edit Record")
                    }
                }
            }
        }
    }
}