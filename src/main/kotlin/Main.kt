import commands.help

var version = "v0.1"

fun main() {
    println("fs.kotlin $version")

    while (isLooping) {
        var input = readln()

        when (input) {
            "help" -> help(version)
            "exit" -> break
            else   -> println("KTFS01: $input not found. type \"help\" for help.")
        }
    }
}
