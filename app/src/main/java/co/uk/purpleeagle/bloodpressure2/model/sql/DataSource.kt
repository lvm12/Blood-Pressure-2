package co.uk.purpleeagle.bloodpressure2.model.sql

import app.cash.sqldelight.db.SqlDriver
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus
import records.RecordQueries
import records.SavedRecord

class DataSource(
    driver: SqlDriver
) : DataSourceI {
    private val queries = RecordQueries(
        driver = driver,
        SavedRecordAdapter = SavedRecord.Adapter(
            statusAdapter = recordStatusAdapter
        )
    )
    override suspend fun getAll(): List<Record> {
        return queries
            .getAll()
            .executeAsList()
            .map { it.toRecord() }
    }

    override suspend fun getById(id: Long): Record {
        return queries
            .getById(id)
            .executeAsOne()
            .toRecord()
    }

    override suspend fun getByStatus(status: RecordStatus): List<Record> {
        return queries
            .getByStatus(status)
            .executeAsList()
            .map { it.toRecord() }
    }

    override suspend fun insert(record: Record) {
        queries
            .insert(record.toSavedRecord())
    }

    override suspend fun deleteById(id: Long) {
        queries
            .deleteById(id)
    }

    override suspend fun deleteByStatus(status: RecordStatus) {
        queries
            .getByStatus(status)
    }

    override suspend fun getAllByStatus(statuses: List<RecordStatus>): List<Record> {
        val records = mutableListOf<Record>()
        statuses.forEach { status ->
            val temp = queries
                .getByStatus(status)
                .executeAsList()
            temp.forEach {
                records.add(it.toRecord())
            }
        }
        return records
    }
}