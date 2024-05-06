package co.uk.purpleeagle.bloodpressure2

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import co.uk.purpleeagle.bloodpressure2.model.sql.DataSource
import co.uk.purpleeagle.bloodpressure2.model.sql.DatabaseDriverFactory
import co.uk.purpleeagle.bloodpressure2.ui.theme.BloodPressure2Theme
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event
import co.uk.purpleeagle.bloodpressure2.viewmodel.Navigator
import co.uk.purpleeagle.bloodpressure2.viewmodel.ViewModel
import co.uk.purpleeagle.bloodpressure2.viewmodel.ViewModelFactory

lateinit var navigationWrapper: Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigator = rememberNavController()
            val dataSource = DataSource(
                driver = DatabaseDriverFactory(this).create()
            )
            val factory = ViewModelFactory(
                navigator = navigator,
                dataSource = dataSource,
                applicationContext
            )
            val viewModel = ViewModelProvider(this, factory)[ViewModel::class.java]
            navigationWrapper = Navigator(
                uiState = viewModel.uiState.collectAsState().value,
                modelState = viewModel.modelState.collectAsState().value,
                dataSource = dataSource,
                varOnEvent = viewModel::onEvent
            )
            BloodPressure2Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    App(
                        viewModel = viewModel,
                        navigator = navigator
                    )
                }
            }
        }
    }
}