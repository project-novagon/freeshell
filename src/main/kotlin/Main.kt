import api.printConsoleError
import commands.about
import commands.help
import java.net.InetAddress
import java.io.BufferedReader
import java.io.InputStreamReader

var version = "v3.0.0.2.od"
var username = System.getProperty("user.name")
var computername = InetAddress.getLocalHost().hostName
var dir = "~/"
var cursor  = "${ANSIHeaders.BLUE}($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
var userinf = ""

fun main() {
    println("fs.kotlin $version")

    while (true) {
        print(cursor)
        var input = readln()

        when (input) {
            "fs help"  -> help(version)
            "exit"     -> break
            "fs about" -> about(version)
            else       -> executeCommand(input)
            // else    -> printConsoleError("FS01", input)
        }
    }
}

private fun executeCommand(command: String) {
    try {
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            println(line)
        }

    } catch (e: java.io.IOException) {
        printConsoleError("FS02", command)
    }
}
