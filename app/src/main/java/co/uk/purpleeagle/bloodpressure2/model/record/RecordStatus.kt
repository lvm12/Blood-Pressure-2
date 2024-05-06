package co.uk.purpleeagle.bloodpressure2.model.record

enum class RecordStatus(val string: String) {
    NEW("New"),
    EXPORTED("Exported"),
    ARCHIVED("Archived"),
    IMPORT("Import..."),
    BOTH("Both")
}