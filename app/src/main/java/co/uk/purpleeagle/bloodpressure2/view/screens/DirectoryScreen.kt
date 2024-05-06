package co.uk.purpleeagle.bloodpressure2.view.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import co.uk.purpleeagle.bloodpressure2.R
import co.uk.purpleeagle.bloodpressure2.dataStore
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.Pages
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DirectoryScreen(
    uiState: UiState,
    modelState: ModelState,
    onEvent: (Event) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            CoroutineScope(Dispatchers.IO).launch {
                val uri = it.data?.data ?: return@launch
                val takeFlags =
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, takeFlags)
                context.dataStore.edit {
                    it[stringPreferencesKey("uri")] = uri.toString()
                }
                onEvent(Event.UriGranted)
                onEvent(Event.Navigate(Pages.CSV))
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.requestFolderAccess)
        )
        Spacer(modifier = Modifier
            .height(10.dp)
        )
        IconButton(
            onClick = {
                launcher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE))
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.requestFolderAccessContentDescription),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}