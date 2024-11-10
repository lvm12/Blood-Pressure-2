package co.uk.purpleeagle.bloodpressure2.view.components.graph

import androidx.compose.runtime.Composable
import co.uk.purpleeagle.bloodpressure2.model.record.Record


@Composable
fun Graph(
    records: List<Record>
) {
//    val categories = remember {
//        val list = mutableListOf<String>()
//        records.forEach { list.add(
//            "${it.createdAt.toDate()}${
//                if (
//                    it.comment.isNotBlank()
//                    || !(it.comment != "N/A")
//                ) ":${it.comment}"
//                else ""
//            }"
//        ) }
//        list
//    }
//    val diaPoints = remember {
//        val list = mutableListOf<Float>()
//        records.forEach { list.add(it.diastolicPressure.toFloat()) }
//        list.toList()
//    }
//    val sysPoints = remember {
//        val list = mutableListOf<Float>()
//        records.forEach { list.add(it.systolicPressure.toFloat()) }
//        list.toList()
//    }
//    val pulPoints = remember {
//        val list = mutableListOf<Float>()
//        records.forEach { list.add(it.pulse.toFloat()) }
//        list.toList()
//    }
//    val items = remember {
//        listOf(
//            "Diastolic" to diaPoints,
//            "Systolic" to sysPoints,
//            "Pulse" to pulPoints
//        )
//    }
//    val dataSet = remember {
//        MultiChartDataSet(
//            items = items,
//            categories = categories,
//            title = "Pressures"
//        )
//    }
//    LineChartView(
//        dataSet = dataSet,
//        style = LineChartDefaults.style(
//            lineColors = listOf(
//                MaterialTheme.colorScheme.primary,
//                MaterialTheme.colorScheme.secondary,
//                MaterialTheme.colorScheme.tertiary
//            )
//        )
//    )
}
