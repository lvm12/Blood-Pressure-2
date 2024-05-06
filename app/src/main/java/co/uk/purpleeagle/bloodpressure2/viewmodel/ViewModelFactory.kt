package co.uk.purpleeagle.bloodpressure2.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavHostController
import co.uk.purpleeagle.bloodpressure2.model.sql.DataSource

class ViewModelFactory(
    private val navigator: NavHostController,
    private val dataSource: DataSource,
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ViewModel(navigator, dataSource, context) as T
        }
        throw IllegalArgumentException("View model does not exist")
    }
}