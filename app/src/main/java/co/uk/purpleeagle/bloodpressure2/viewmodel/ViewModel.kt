package co.uk.purpleeagle.bloodpressure2.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import co.uk.purpleeagle.bloodpressure2.model.csv.Csv
import co.uk.purpleeagle.bloodpressure2.model.record.RecordEditSource
import co.uk.purpleeagle.bloodpressure2.model.record.RecordField
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import co.uk.purpleeagle.bloodpressure2.model.record.emptyRecord
import co.uk.purpleeagle.bloodpressure2.model.sql.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val navigator: NavHostController,
    private val dataSource: DataSource,
    private val context: Context
): ViewModel() {
    private val _modelState = MutableStateFlow(ModelState(context))
    private val _uiState = MutableStateFlow(UiState())
    init {
        viewModelScope.launch {
            _modelState.update {
                it.copy(
                    currentRecords = dataSource.getAllByStatus(
                        _modelState.value.selected
                    )
                )
            }
        }
    }
    val modelState = _modelState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ModelState(context = context)
        )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        UiState()
    )

    fun onEvent(event: Event){
        viewModelScope.launch {
            when(event){
                is Event.Navigate -> {
                    navigator.navigate(event.page.route)
                    _uiState.update { it.copy(
                        page = event.page
                    ) }
                    event.page.onNavigate()
                }
                Event.ManageCsvToast -> {
                    _uiState.update { it.copy(
                        showCsvToast = !it.showCsvToast
                    ) }
                }
                is Event.CsvFileName -> {
                    _uiState.update { it.copy(
                        csvFileName = event.newValue
                    ) }
                }
                Event.GenerateCSV -> {
                    val csv = Csv(
                        modelState = _modelState.value,
                        uiState = _uiState.value,
                        dataSource = dataSource,
                        context = context,
                        onEvent = ::onEvent
                    )
                    csv.generateData()
                    csv.saveData()
                    _modelState.value.currentRecords.forEach {
                        dataSource.insert(
                            it.copy(status = RecordStatus.EXPORTED)
                        )
                    }
                    _modelState.update { it.copy(
                        currentRecords = it.currentRecords.map {record ->
                            record.copy(
                                status = RecordStatus.EXPORTED
                            )
                        }
                    ) }
                }
                is Event.GoToEditScreen -> {
                    _modelState.update { it.copy(
                        currentRecord = event.record,
                        currentRecordSource = event.source
                    ) }
                    onEvent(Event.Navigate(Pages.EDIT))
                }
                is Event.OnValueChanged -> {
                    _modelState.update { it.copy(
                        currentRecord = it.currentRecord.copy(
                            systolicPressure =
                                if (event.field == RecordField.SYSTOLIC) event.newValue
                                else it.currentRecord.systolicPressure,
                            diastolicPressure =
                                if (event.field == RecordField.DIASTOLIC) event.newValue
                                else it.currentRecord.diastolicPressure,
                            pulse =
                                if (event.field == RecordField.PULSE) event.newValue
                                else it.currentRecord.pulse,
                            comment =
                                if (event.field == RecordField.COMMENT) event.newValue
                                else it.currentRecord.comment
                        )
                    ) }
                }
                Event.SaveRecord -> {
                    if (_modelState.value.currentRecordSource == RecordEditSource.NEW){
                        dataSource.insert(
                            _modelState.value.currentRecord.copy(
                                id = dataSource.getAll().size.toLong()
                            )
                        )
                    }else{
                        dataSource.insert(
                            _modelState.value.currentRecord
                        )
                    }
                    _modelState.update { it.copy(
                        currentRecord = emptyRecord(),
                        currentRecords = dataSource.getAllByStatus(
                            _modelState.value.selected
                        )
                    ) }
                    onEvent(Event.Navigate(Pages.LIST))
                }
                Event.UriGranted -> {
                    _modelState.update { it.copy(
                        hasDirectory = true
                    ) }
                }
                is Event.ManageBottomSheet -> {
                    _uiState.update { it.copy(
                        showBottomSheet = event.show
                    ) }
                    _modelState.update { it.copy(
                        currentRecord =
                            if (event.show) event.record
                            else emptyRecord()
                    ) }
                }

                is Event.DeleteRecord -> {
                    dataSource.deleteById(
                        event.record.id
                    )
                    _modelState.update { it.copy(
                        currentRecords = dataSource.getAllByStatus(
                            _modelState.value.selected
                        )
                    ) }
                    onEvent(Event.ManageBottomSheet(
                        show = false,
                        record = emptyRecord()
                    ))
                }

                is Event.ManageDropdown -> {
                    if (event.selected != RecordStatus.IMPORT) {
                        _uiState.update {
                            it.copy(
                                showDropdown = event.show
                            )
                        }
                        if (event.selected != null) {
                            _modelState.update {
                                it.copy(
                                    selected =
                                        if (event.selected == RecordStatus.BOTH) listOf(RecordStatus.NEW, RecordStatus.EXPORTED)
                                        else listOf(event.selected)
                                )
                            }
                        }
                        _modelState.update { it.copy(
                             currentRecords = dataSource.getAllByStatus(
                                 _modelState.value.selected
                             )
                        ) }
                    }
                }
            }
        }
    }
}