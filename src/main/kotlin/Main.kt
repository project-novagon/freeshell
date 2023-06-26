import commands.about
import commands.help

var version = "v3.0.0.1.od"

fun main() {
    println("fs.kotlin $version")

    while (true) {
        var input = readln()

        when (input) {
            "help" -> help(version)
            "exit" -> break
            "fs about" -> about(version)
            else -> println("KTFS01: $input not found. type \"help\" for help.")
        }
    }
}