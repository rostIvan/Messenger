package trickyquestion.messenger.util

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"
const val LINE = "=================================================================================="

enum class Color{
    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    CYAN,
    WHITE
}
enum class Mode {
    NEW_LINE,
    WITHOUT_NEW_LINE
}

fun log(text: Any, color: Color = Color.WHITE) {
    println(getAnsiColor(color) + text.toString() + ANSI_RESET)
}

fun log(text: Any) {
    println(text.toString())
}

fun log(text: Any, color: Color = Color.WHITE, mode: Mode = Mode.NEW_LINE) {
    when(mode) {
        Mode.NEW_LINE -> println(getAnsiColor(color) + text.toString() + ANSI_RESET)
        Mode.WITHOUT_NEW_LINE -> print(getAnsiColor(color) + text.toString() + ANSI_RESET)
    }
}

fun log(text: Any, mode: Mode = Mode.NEW_LINE) {
    when(mode) {
        Mode.NEW_LINE -> println(text.toString())
        Mode.WITHOUT_NEW_LINE -> print(text.toString())
    }
}

private fun getAnsiColor(color: Color) = when(color) {
    Color.BLACK -> ANSI_BLACK
    Color.RED -> ANSI_RED
    Color.GREEN -> ANSI_GREEN
    Color.YELLOW -> ANSI_YELLOW
    Color.BLUE -> ANSI_BLUE
    Color.PURPLE -> ANSI_PURPLE
    Color.CYAN -> ANSI_CYAN
    Color.WHITE -> ANSI_WHITE
}