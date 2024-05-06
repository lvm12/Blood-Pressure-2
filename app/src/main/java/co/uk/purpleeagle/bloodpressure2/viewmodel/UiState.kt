package co.uk.purpleeagle.bloodpressure2.viewmodel

data class UiState(
    val isBottomSheetShowing: Boolean = false,
    val page: Pages = Pages.LIST,
    val showCsvToast: Boolean = false,
    val csvFileName: String = "",
    val showBottomSheet: Boolean = false,
    val showDropdown: Boolean = false
)
