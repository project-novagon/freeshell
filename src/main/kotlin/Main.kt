import api.printConsoleError
import commands.about
import commands.help
import java.net.InetAddress
import java.io.BufferedReader
import java.io.InputStreamReader

var version = "v3.0.0.1.od"
var username = System.getProperty("user.name")
var computername = InetAddress.getLocalHost().hostName
var cursor  = "($username @ $computername ) ~>"

fun main() {
    println("fs.kotlin $version")

    while (true) {
        print("${Color.GREEN} $cursor ${Color.WHITE}")
        var input = readln()

        when (input) {
            "fs help"  -> help(version)
            "fs exit"  -> break
            "fs about" -> about(version)
            else       -> executeCommand(input)
        }
    }
}

private fun executeCommand(command: String) {
    val process = Runtime.getRuntime().exec(command)
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    var line: String?

    while (reader.readLine().also { line = it } != null) {
        println(line)
    }

    if (process.waitFor() != 0) {
        api.printConsoleError("FS01", command)
    }
}
