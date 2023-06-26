import api.printConsoleError
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
        print("${Color.GREEN} $cursor ${Color.WHITE}")
        var input = readln()

        when (input) {
            "help"     -> help(version)
            "exit"     -> break
            "fs about" -> about(version)
            else       -> printConsoleError("FS01", input)
        }
    }
}
