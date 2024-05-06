package co.uk.purpleeagle.bloodpressure2.model.util

fun <T> List<T>.cAdd(element: T): List<T>{
    val mut = toMutableList()
    mut.add(element)
    return mut.toList()
}