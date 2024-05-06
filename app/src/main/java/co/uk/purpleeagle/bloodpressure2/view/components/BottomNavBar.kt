package co.uk.purpleeagle.bloodpressure2.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.Pages
import co.uk.purpleeagle.bloodpressure2.viewmodel.UiState
import co.uk.purpleeagle.bloodpressure2.viewmodel.navPages

@Composable
fun BottomNavBar(
    uiState: UiState,
    modifier: Modifier,
    onEvent: (Event) -> Unit
){
    NavigationBar(
        modifier = modifier
    ) {
        navPages.forEach { page ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = page.imageVector,
                        contentDescription = page.label
                    )
                },
                label = { Text(text = page.label) },
                selected = uiState.page == page,
                onClick = { onEvent(Event.Navigate(page)) },
            )
        }
    }
}
