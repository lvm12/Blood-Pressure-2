package co.uk.purpleeagle.bloodpressure2.view.components.edit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EditTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        placeholder = {Text(text = placeholder)},
        isError = isError,
        supportingText = {
            if (isError){
                Text(
                    text = "Must only be a number",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )

}