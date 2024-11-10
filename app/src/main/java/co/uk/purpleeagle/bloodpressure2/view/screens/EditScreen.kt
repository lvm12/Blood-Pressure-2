package co.uk.purpleeagle.bloodpressure2.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.uk.purpleeagle.bloodpressure2.model.record.RecordField
import co.uk.purpleeagle.bloodpressure2.view.components.edit.EditTextField
import co.uk.purpleeagle.bloodpressure2.view.components.edit.RecordValidator
import co.uk.purpleeagle.bloodpressure2.view.components.edit.inside
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState

@Composable
fun EditScreen(
    modelState: ModelState,
    onEvent: (Event) -> Unit
) {
    var systolicError by remember {
        mutableStateOf(false)
    }
    var diastolicError by remember {
        mutableStateOf(false)
    }
    var pulseError by remember {
        mutableStateOf(false)
    }
    val diaFr = remember {
        FocusRequester()
    }
    val pulFr = remember {
        FocusRequester()
    }
    val comFr = remember {
        FocusRequester()
    }
    val sysFr = remember{
        FocusRequester()
    }
    LaunchedEffect(modelState.currentRecord.systolicPressure.length == 3){
        if (modelState.currentRecord.systolicPressure.length == 3) {
            diaFr.requestFocus()
        }
    }
    LaunchedEffect(modelState.currentRecord.diastolicPressure.length == 2){
        if (modelState.currentRecord.diastolicPressure.length == 2) {
            pulFr.requestFocus()
        }
    }
    LaunchedEffect(modelState.currentRecord.pulse.length == 2){
        if (modelState.currentRecord.pulse.length == 2) {
            comFr.requestFocus()
        }
    }
    LaunchedEffect(key1 = modelState.currentRecord.systolicPressure.isBlank()) {
        if (modelState.currentRecord.systolicPressure.isBlank())
            sysFr.requestFocus()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Enter details",
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.height(70.dp)
            ){
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
                EditTextField(
                    value = modelState.currentRecord.systolicPressure,
                    onValueChanged = {
                        onEvent(Event.OnValueChanged(
                            newValue = it,
                            field = RecordField.SYSTOLIC
                        ))
                    },
                    placeholder = "Systolic",
                    isError = systolicError,
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .weight(8f)
                        .focusRequester(sysFr)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
                EditTextField(
                    value = modelState.currentRecord.diastolicPressure,
                    onValueChanged = {
                        onEvent(Event.OnValueChanged(
                            newValue = it,
                            field = RecordField.DIASTOLIC
                        ))
                    },
                    placeholder = "Diastolic",
                    isError = diastolicError,
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .weight(8f)
                        .focusRequester(diaFr)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
                EditTextField(
                    value = modelState.currentRecord.pulse,
                    onValueChanged = {
                        onEvent(Event.OnValueChanged(
                            newValue = it,
                            field = RecordField.PULSE
                        ))
                    },
                    placeholder = "Pulse",
                    isError = pulseError,
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .weight(8f)
                        .focusRequester(pulFr)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
            }
            EditTextField(
                value = modelState.currentRecord.comment,
                onValueChanged = {
                    onEvent(Event.OnValueChanged(
                        newValue = it,
                        field = RecordField.COMMENT
                    ))
                },
                placeholder = "Comment",
                isError = false,
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth(0.925f)
                    .focusRequester(comFr)
            )
        }
        Button(
            onClick = {
                val errors = RecordValidator.validate(
                    modelState.currentRecord
                )
                if (!(true inside errors)) onEvent(Event.SaveRecord)
                else {
                    systolicError = errors.systolic
                    diastolicError = errors.diastolic
                    pulseError = errors.pulse
                }
            }
        ) {
            Text(text = "Save")
        }
    }
}