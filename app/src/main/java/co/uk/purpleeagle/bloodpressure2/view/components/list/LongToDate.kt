package co.uk.purpleeagle.bloodpressure2.view.components.list

import kotlinx.datetime.*

fun Long.toDate(): String{
    return Instant
        .fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
        .format(LocalDate.Formats.ISO_BASIC)
        .datify()
}

fun String.datify(): String{
    val year = take(4)
    val day = takeLast(2)
    val month = substring(4..5)
    return "$day/$month/$year"
}