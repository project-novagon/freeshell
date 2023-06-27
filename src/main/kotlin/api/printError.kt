package api
import ANSIHeaders

fun printConsoleError(code:String, input: String) {
    println("${ANSIHeaders.RED}$code${ANSIHeaders.RESET}: $input not found. type \"fs help\" for help.")
}