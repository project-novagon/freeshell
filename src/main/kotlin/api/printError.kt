package api
import ANSIHeaders

fun printConsoleError(code:String, input: String) {
    println("${ANSIHeaders.RED}$code${ANSIHeaders.WHITE}: $input not found. type \"help\" for help.")
}