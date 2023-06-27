package api
import ANSIHeaders

fun printConsoleError(code:String, input: String) {
    println("${ANSIHeaders.RED}$code${ANSIHeaders.RESET}: $input not found. type \"fs help\" for help.")
}
// TODO: Question System
// println("${ANSIHeaders.CYAN} ? Are you sure? (${ANSIHeaders.GREEN}y${ANSIHeaders.CYAN}/${ANSIHeaders.RED}${ANSIHeaders.BOLD}N${ANSIHeaders.CYAN})${ANSIHeaders.RESET}")
