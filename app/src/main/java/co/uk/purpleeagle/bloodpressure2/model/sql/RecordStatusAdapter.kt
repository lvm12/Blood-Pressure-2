package co.uk.purpleeagle.bloodpressure2.model.sql

import app.cash.sqldelight.ColumnAdapter
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus

val recordStatusAdapter = object : ColumnAdapter<RecordStatus, String> {
    override fun decode(databaseValue: String): RecordStatus {
        return when (databaseValue){
            "n" -> RecordStatus.NEW
            "e" -> RecordStatus.EXPORTED
            else -> throw Exception("Column did not contain valid status")
        }
    }

    override fun encode(value: RecordStatus): String {
        return when (value){
            RecordStatus.NEW -> "n"
            RecordStatus.EXPORTED -> "e"
            else -> throw Exception("Not a valid status")
        }
    }
}