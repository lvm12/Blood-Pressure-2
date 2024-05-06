package co.uk.purpleeagle.bloodpressure2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.uk.purpleeagle.bloodpressure2.viewmodel.Navigator
import co.uk.purpleeagle.bloodpressure2.viewmodel.Pages
import co.uk.purpleeagle.bloodpressure2.viewmodel.ViewModel

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

@Composable
fun App(
    viewModel: ViewModel,
    navigator: NavHostController
) {
    val modelState by viewModel.modelState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navigator,
        startDestination = Pages.LIST.route
    ){
        composable(Pages.LIST.route){
            Pages.LIST.screen(
                uiState,
                modelState,
                viewModel::onEvent
            )
        }
        composable(Pages.CSV.route){
            Pages.CSV.screen(
                uiState,
                modelState,
                viewModel::onEvent
            )
        }
        composable(Pages.EDIT.route){
            Pages.EDIT.screen(
                uiState,
                modelState,
                viewModel::onEvent
            )
        }
        composable(Pages.DIRECTORY.route){
            Pages.DIRECTORY.screen(
                uiState,
                modelState,
                viewModel::onEvent
            )
        }
        composable(Pages.GRAPH.route){
            Pages.GRAPH.screen(
                uiState,
                modelState,
                viewModel::onEvent
            )
        }
    }
}