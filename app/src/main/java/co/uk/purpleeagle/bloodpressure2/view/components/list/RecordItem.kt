package co.uk.purpleeagle.bloodpressure2.view.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.rounded.CommentsDisabled
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.uk.purpleeagle.bloodpressure2.model.record.Record
import co.uk.purpleeagle.bloodpressure2.viewmodel.Event

@Composable
fun RecordItem(
    record: Record,
    usesDate: Boolean = true,
    usesComment: Boolean = true,
    onEvent: (Event) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(
                    Event.ManageBottomSheet(
                        show = true,
                        record = record
                    )
                )
            },
        horizontalArrangement =
            if (usesDate) Arrangement.SpaceAround
            else Arrangement.Start
    ){
        if (!usesDate){
            Spacer(modifier = Modifier.width(60.dp))}
        Text(text =
            if (usesDate) record.createdAt.toDate()
            else "Date"
        )
        if (!usesDate){
            Spacer(modifier = Modifier.width(64.dp))}
        Text(text = record.systolicPressure)
        if (!usesDate){
            Spacer(modifier = Modifier.width(38.dp))}
        Text(text = record.diastolicPressure)
        if (!usesDate){
            Spacer(modifier = Modifier.width(35.dp))}
        Text(text = record.pulse)
        if (!usesDate){
            Spacer(modifier = Modifier.width(22.dp))}
        if (usesComment) {
            if (record.comment.isNotBlank()) {
                Icon(
                    imageVector =
                    Icons.AutoMirrored.Rounded.Comment,
                    contentDescription =
                    "Has comment"
                )
            }else{
                Spacer(modifier = Modifier.width(25.dp))
            }
        }else{
            Text(text = record.comment)
        }
    }
}