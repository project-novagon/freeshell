import commands.about
import commands.help
import java.net.InetAddress

var version = "v3.0.0.1.od"
var username = System.getProperty("user.name")
var computername = InetAddress.getLocalHost().hostName
var cursor  = "($username @ $computername ) ~>"

fun main() {
    println("fs.kotlin $version")

    while (true) {
        print("\u001B[32m" + cursor + " \u001B[0m")
        var input = readln()

        when (input) {
            "help"     -> help(version)
            "exit"     -> break
            "fs about" -> about(version)
            else       -> println("\u001B[31mFS01\u001B[0m: $input not found. type \"help\" for help.")
        }
    }
}
