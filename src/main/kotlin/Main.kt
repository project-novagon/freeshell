import api.printConsoleError
import commands.about
import commands.help
import java.net.InetAddress
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit
import java.io.File
import kotlin.system.exitProcess

var version = "v3.0.0.2.od"
var username = System.getProperty("user.name")
var computername = InetAddress.getLocalHost().hostName
var dir = System.getProperty("user.dir")
var homedir = System.getProperty("user.home")
var cursor  = "($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
var userinf = ""

var shellCommands = arrayOf("fs", "exit", "cd")

fun main() {
    println("fs.kotlin $version")

    while (true) {
        print("$cursor")
        var input = readln().split(" ")

        if (shellCommands.contains(input[0])) {
            executeCommand(true, input)
        } else {
            executeCommand(false, input)
        }
    }
}

private fun executeCommand(isInternal: Boolean, command: List<String>) {
    if (isInternal) {
        when (command[0]) {
            "fs" -> {
                when (command[1]) {
                    "help" -> help(version)
                    "about" -> about(version)
                }
            }

            "cd" -> {
                try {
                    dir = command[1]
                    cursor  = "($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
                } catch (e: Exception) {
                    dir = homedir 
                    cursor  = "($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
                }
            }

            "exit" -> exitProcess(0)
        }
    } else {
        try {
            command.joinToString(separator=" ").run(File(dir))
        } catch (e: Exception) {
            printConsoleError("FS02", command.joinToString(separator=" "))
        }
    }
}

fun String.run(workingDir: File? = null) {
    val process = ProcessBuilder(*split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(Redirect.INHERIT)
            .redirectError(Redirect.INHERIT)
            .start()
    if (!process.waitFor(10, TimeUnit.SECONDS)) {
        process.destroy()
        throw RuntimeException("execution timed out: $this")
    }
    if (process.exitValue() != 0) {
        throw RuntimeException("execution failed with code ${process.exitValue()}: $this")
    }
}