package api
import Color

fun printConsoleError(code:String, input: String) {
    println("${Color.RED}$code${Color.WHITE}: $input not found. type \"help\" for help.")
}