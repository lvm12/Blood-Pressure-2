package co.uk.purpleeagle.bloodpressure2.viewmodel

import co.uk.purpleeagle.bloodpressure2.model.record.*

sealed interface Event {
    data class Navigate(val page: Pages): Event
    data object ManageCsvToast: Event
    data class CsvFileName(val newValue: String): Event
    data object GenerateCSV: Event
    data class GoToEditScreen(
        val source: RecordEditSource,
        val record: Record = emptyRecord()
    ): Event
    data class OnValueChanged(
        val newValue: String,
        val field: RecordField
    ): Event
    data object SaveRecord: Event
    data object UriGranted: Event
    data class ManageBottomSheet(
        val show: Boolean,
        val record: Record
    ): Event
    data class ManageDropdown(
        val show: Boolean,
        val selected: RecordStatus?
    ): Event
    data class DeleteRecord(val record: Record): Event
}