package co.uk.purpleeagle.bloodpressure2.model.sql

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import records.RecordsDB


class DatabaseDriverFactory (private val context: Context){
    fun create(): SqlDriver{
        return AndroidSqliteDriver(
            schema = RecordsDB.Schema,
            context = context,
            name = "RecordsDB.db"
        )
    }
}