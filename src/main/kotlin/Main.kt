import api.printConsoleError
import commands.about
import commands.help
import java.net.InetAddress
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit
import java.io.File
import kotlin.system.exitProcess
var version = "v3.0.0.4.od"
var username = System.getProperty("user.name")
var osname = System.getProperty("os.name")
var computername = InetAddress.getLocalHost().hostName
var dir = System.getProperty("user.dir")
var homedir = System.getProperty("user.home")
var cursor  = "${ANSIHeaders.CYAN}($username @ $computername : $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
var shellCommands = arrayOf("fs", "exit", "cd", "fpm")
val freeshellLinuxPath = File("/home/$username/.local/share/.freeshell")
val freeshellWindowsPath = File("%APPDATA%\\.freeshell")
val fshLinuxExists = freeshellLinuxPath.exists()
val fshWindowsExists = freeshellWindowsPath.exists()
fun main(args: Array<String>){
    if (osname.startsWith("Windows")){
        println("${ANSIHeaders.YELLOW} NOTE: ${ANSIHeaders.RESET}Windows commands are not well supported")
    }
    if ("-d" in args && args.isNotEmpty()) {
        println("DEBUG MODE ENABLED.")
        println(username)
        println(osname)
        println(freeshellLinuxPath)
        println(freeshellWindowsPath)
        println(freeshellLinuxPath.exists())

    }

    if (fshLinuxExists == false && freeshellLinuxPath.isDirectory || fshWindowsExists == false && freeshellWindowsPath.isDirectory) {
        println("${ANSIHeaders.YELLOW} WARN: ${ANSIHeaders.RESET}Freeshell Config not found. Continuing with setup.")
        Thread.sleep(2000)
        //TODO: make the rest of the setup
        //curl -OJLs https://github.com/project-novagon/fpm/releases/download/v1.2.0/fpm.py
    } else {

        while (true) {
            print(cursor)
            val input = readln().split(" ")

            if (shellCommands.contains(input[0])) {
                executeCommand(true, input)
            } else {
                executeCommand(false, input)
            }
        }
    }
}

private fun executeCommand(isInternal: Boolean, command: List<String>) {
    if (isInternal) {
        when (command[0]) {
            "fs" -> {
                try {
                    when (command[1]) {
                        "help" -> help(version)
                        "about" -> about(version)
                    } 
                } catch (e: Exception) {
                    help(version)
                }
            }

            "cd" -> {
                try {
                    dir = command[1]
                    cursor  = "${ANSIHeaders.BLUE}($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
                } catch (e: Exception) {
                    dir = homedir 
                    cursor  = "${ANSIHeaders.BLUE}($username @ $computername // $dir )${ANSIHeaders.GREEN} ~> ${ANSIHeaders.RESET}"
                }
            }

            "exit" -> exitProcess(0)
        }
    } else {
        try {
            command.joinToString(separator=" ").run(File(dir))
        } catch (e: Exception) {
            printConsoleError("FS01", command.joinToString(separator=" "))
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