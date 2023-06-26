import commands.help

var version = "v0.1"

fun main() {
    println("fs.kotlin $version")

    var isLooping = true
    while (isLooping) {
        var input = readln()

        when (input) {
            "help" -> help(version)
            "exit" -> isLooping = false
            else -> println("KTFS01: $input not found. type \"help\" for help.")
        }
    }
}