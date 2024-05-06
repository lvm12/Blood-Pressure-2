package co.uk.purpleeagle.bloodpressure2.model.csv

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.documentfile.provider.DocumentFile
import co.uk.purpleeagle.bloodpressure2.dataStore
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import co.uk.purpleeagle.bloodpressure2.model.sql.DataSource
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.ModelState
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*

class Csv(
    private val modelState: ModelState,
    private val uiState: UiState,
    private val dataSource: DataSource,
    private val onEvent: (Event) -> Unit,
    private val context: Context
) {
    private var csvData = "IND,DAT,SYS,DIA,PUL,COM\n"

    fun generateData(){
        CoroutineScope(Dispatchers.IO).launch {
            val records = dataSource.getAllByStatus(
                modelState.selected
            )
            records.forEachIndexed {index, i->
                csvData += "${index.toString()},"
                csvData += "${i.createdAt.toCSVDate()},"
                csvData += "${i.systolicPressure},"
                csvData += "${i.diastolicPressure},"
                csvData += "${i.pulse},"
                csvData += "${
                    i.comment.ifEmpty { "N/A" }
                }\n"
            }
        }
    }
    fun saveData(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val uri = context.dataStore.data.map {
                    it[stringPreferencesKey("uri")]
                }.first()?.toUri() ?: throw IOException("Uri not existent")

                val root = DocumentFile.fromTreeUri(
                    context, uri
                ) ?: throw IOException("Uri not valid")
                val file = root.createFile(
                    "text/csv",
                    uiState.csvFileName
                        .ifEmpty { Clock.System.now().toEpochMilliseconds().fileDate() }
                ) ?: throw IOException("Failed to create file")
                val output = context.contentResolver.openOutputStream(file.uri)
                    ?: throw IOException("Failed to open output stream")
                output.use {
                    it.write(csvData.toByteArray())
                }
                onEvent(Event.ManageCsvToast)
                delay(2000L)
                onEvent(Event.ManageCsvToast)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    fun readData(): List<Record>{
        val uri = modelState.importUri ?: throw IOException("No uri")
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IOException("Not a valid uri")
        val data = mutableListOf<Record>()
        inputStream.use {stream ->
            val reader = stream.bufferedReader()
            reader.use {
                val stringLines = reader.readLines().toMutableList()
                stringLines.removeAt(0)
                stringLines.forEach {
                    val split = it.split(",")
                    data.add(Record(
                        id = 0,
                        systolicPressure = split[2],
                        diastolicPressure = split[3],
                        pulse = split[4],
                        comment = split[5],
                        status = RecordStatus.IMPORT,
                        createdAt = split[1].toCreatedAt(),
                    ))
                }
            }
        }
        return data
    }
}

fun String.toCreatedAt(): Long{
    val split = split("-")
    val date = LocalDate(
        year = split[0].toInt(),
        monthNumber = split[2].toInt(),
        dayOfMonth = split[2].toInt()
    )
    return (date.year * 31556952000L + date.monthNumber * 2629746000L + date.dayOfMonth * 86400)
}
private fun Long.toCSVDate(): String{
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return format.format(date)
}

private fun Long.fileDate(): String{
    val date = Date(this)
    val format = SimpleDateFormat("yyyy_MM_dd_HH_mm", Locale.ENGLISH)
    return format.format(date)
}