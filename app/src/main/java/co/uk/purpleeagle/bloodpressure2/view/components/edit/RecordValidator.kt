package co.uk.purpleeagle.bloodpressure2.view.components.edit

import androidx.core.text.isDigitsOnly
import co.uk.purpleeagle.bloodpressure2.model.record.Record

object RecordValidator {
    fun validate(record: Record): RecordErrors{
        var errors = RecordErrors()
        if (!record.systolicPressure.isDigitsOnly()) errors = errors.copy(
            systolic = true
        )
        if (!record.diastolicPressure.isDigitsOnly()) errors = errors.copy(
            diastolic = true
        )
        if (!record.pulse.isDigitsOnly()) errors = errors.copy(
            pulse = true
        )
        return errors
    }
}
data class RecordErrors(
    val systolic: Boolean = false,
    val diastolic: Boolean = false,
    val pulse: Boolean = false
)

infix fun Boolean.inside(errors: RecordErrors): Boolean{
    return errors.pulse || errors.systolic || errors.diastolic
}