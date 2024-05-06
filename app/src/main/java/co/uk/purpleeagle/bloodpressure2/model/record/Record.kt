package co.uk.purpleeagle.bloodpressure2.model.record

import kotlinx.datetime.Clock

data class Record(
    val id: Long,
    val systolicPressure: String,
    val diastolicPressure: String,
    val pulse: String,
    val comment: String,
    val status: RecordStatus,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds()
)
