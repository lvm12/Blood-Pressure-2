package co.uk.purpleeagle.bloodpressure2.model.sql

import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.model.record.RecordStatus

interface DataSourceI {
    suspend fun getAll(): List<Record>
    suspend fun getById(id: Long): Record
    suspend fun getByStatus(status: RecordStatus): List<Record>
    suspend fun insert(record: Record)
    suspend fun deleteById(id: Long)
    suspend fun deleteByStatus(status: RecordStatus)
    suspend fun getAllByStatus(statuses: List<RecordStatus>): List<Record>
}