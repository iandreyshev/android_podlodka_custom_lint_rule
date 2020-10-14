package ru.iandreyshev.customlintrulehomework

enum class Progress {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;
}

fun main(args: Array<String>) {

    val progress: Progress? = Progress.NOT_STARTED

    when (progress) {
        Progress.NOT_STARTED -> "NOT_STARTED"
        Progress.IN_PROGRESS -> "IN_PROGRESS"
        Progress.COMPLETED -> "COMPLETED"
        else -> "NOT_STARTED"
    }

}
