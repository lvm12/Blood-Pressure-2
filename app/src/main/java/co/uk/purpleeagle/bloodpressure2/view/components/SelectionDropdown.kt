package co.uk.purpleeagle.bloodpressure2.view.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionDropdown(
    nexi: Boolean,
    uiState: UiState,
    modelState: ModelState,
    modifier: Modifier = Modifier,
    onEvent: (Event) -> Unit
) {
    val options = remember {
        if (nexi){
            listOf(
                RecordStatus.NEW,
                RecordStatus.EXPORTED,
                RecordStatus.BOTH,
                RecordStatus.IMPORT
            )
        }else{
            listOf(
                RecordStatus.NEW,
                RecordStatus.EXPORTED,
                RecordStatus.BOTH
            )
        }
    }
    ExposedDropdownMenuBox(
        expanded = uiState.showDropdown,
        onExpandedChange = {
            onEvent(Event.ManageDropdown(
                show = it,
                selected = null
            ))
        }
    ) {
        OutlinedTextField(
            value = 
                if (modelState.selected.size == 2) RecordStatus.BOTH.string
                else modelState.selected[0].string,
            onValueChange = {},
            readOnly = true,
            label = {Text(text = "Choose records")},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.showDropdown)
            },
            modifier = modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = uiState.showDropdown,
            onDismissRequest = {
                onEvent(Event.ManageDropdown(
                    show = false,
                    selected = null
                ))
            }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.string) },
                    onClick = { onEvent(
                        Event.ManageDropdown(
                            show = false,
                            selected = it
                        )
                    ) },
                    modifier = modifier
                )
            }
        }
    }
}