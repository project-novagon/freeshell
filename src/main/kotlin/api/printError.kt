package api
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import terminal

fun printConsoleError(code:String, body: String) {
    println("${red("$code")}: $body")
}

fun questionSystem(element: String, choices: List<String>): String? {
    val response = terminal.prompt("${blue("?:")} $element", choices=choices)
    terminal.println("You Selected: ${blue("$response")}")
    return response
}
fun printConsoleWarning(text: String) {
    terminal.println("${yellow("!:")} $text")
}

fun printConsoleInfo(text: String, isSuccess: Boolean) {
    terminal.println("${if (!isSuccess) blue("i:") else green("i:")} $text")
}