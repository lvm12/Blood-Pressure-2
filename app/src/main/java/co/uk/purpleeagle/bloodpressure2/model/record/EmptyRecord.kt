package co.uk.purpleeagle.bloodpressure2.model.record

import co.uk.purpleeagle.bloodpressure2.model.record.Record
import kotlinx.datetime.Clock

fun emptyRecord(): Record = Record(
    id = 0L,
    systolicPressure = "",
    diastolicPressure = "",
    pulse = "",
    comment = "",
    status = RecordStatus.NEW,
    createdAt = Clock.System.now().toEpochMilliseconds()
)