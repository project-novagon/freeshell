package api


fun printConsoleError(code:String, input: String) {
    println("${ANSIHeaders.RED}$code${ANSIHeaders.RESET}: $input not found. type \"fs help\" for help.")
}
// TODO: Question System

fun QuestionSys(element: String){
    println("${ANSIHeaders.CYAN} ? $element (${ANSIHeaders.GREEN}y${ANSIHeaders.CYAN}/${ANSIHeaders.RED}${ANSIHeaders.BOLD}N${ANSIHeaders.CYAN})${ANSIHeaders.RESET}")
}
