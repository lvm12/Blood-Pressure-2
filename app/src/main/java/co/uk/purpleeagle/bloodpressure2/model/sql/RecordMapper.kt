package co.uk.purpleeagle.bloodpressure2.model.sql

import co.uk.purpleeagle.bloodpressure2.model.record.Record
import records.SavedRecord

fun SavedRecord.toRecord(): Record {
    return Record(
        id = id,
        systolicPressure = systolicPressure,
        diastolicPressure = diastolicPressure,
        pulse = pulse,
        comment = comment,
        status = status,
        createdAt = createdAt
    )
}

fun Record.toSavedRecord(): SavedRecord{
    return SavedRecord(
        id = id,
        systolicPressure = systolicPressure,
        diastolicPressure = diastolicPressure,
        pulse = pulse,
        comment = comment,
        status = status,
        createdAt = createdAt
    )
}