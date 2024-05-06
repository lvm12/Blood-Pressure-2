package co.uk.purpleeagle.bloodpressure2.viewmodel

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.stringPreferencesKey
import co.uk.purpleeagle.bloodpressure2.dataStore
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordEditSource
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import co.uk.purpleeagle.bloodpressure2.model.record.emptyRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

data class ModelState(
    val context: Context,
    val currentRecords: List<Record> = emptyList(),
    val currentRecord: Record = emptyRecord(),
    val currentRecordSource: RecordEditSource? = null,
    val selected: List<RecordStatus> = mutableListOf(RecordStatus.NEW),
    val hasDirectory: Boolean = runBlocking {
        var uri: String? = null
        val uriFlow: Flow<String?> = context.dataStore.data.map {
            it[stringPreferencesKey("uri")]
        }
        uri = uriFlow.first()
        uri != null
    },
    val importUri: Uri? = null
)
