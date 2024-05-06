package co.uk.purpleeagle.bloodpressure2.viewmodel

import co.uk.purpleeagle.bloodpressure2.model.sql.DataSource

class Navigator(
    val uiState: UiState,
    val modelState: ModelState,
    val dataSource: DataSource,
    private val varOnEvent: (Event) -> Unit
) {
    fun navigate(
        page: Pages
    ){
        onEvent(Event.Navigate(page))
    }
    fun onEvent(event: Event){
        varOnEvent(event)
    }
}