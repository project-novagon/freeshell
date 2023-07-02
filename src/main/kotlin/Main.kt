import api.printConsoleError
import api.printConsoleInfo
import api.printConsoleWarning
import api.questionSystem
import com.github.ajalt.mordant.rendering.OverflowWrap
import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import com.github.ajalt.mordant.rendering.Whitespace
import com.github.ajalt.mordant.terminal.Terminal
import commands.about
import commands.help
import java.net.InetAddress
import kotlin.system.exitProcess
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit
import java.io.File

val terminal = Terminal()
var version = "v3.0.0.4.od"
var username = System.getProperty("user.name")
var osname = System.getProperty("os.name")
var computername = InetAddress.getLocalHost().hostName
var dir = System.getProperty("user.dir")
var homedir = System.getProperty("user.home")
var cursor  = ""
var shellCommands = arrayOf("fs", "exit", "cd", "fpm")
val freeshellLinuxPath = File("/home/$username/.local/share/.freeshell")
val freeshellWindowsPath = File("C:/Users/$username/AppData/Roaming/.freeshell")
val strfshlxPath = "/home/$username/.local/share/.freeshell"
val strfshwinPath = "C:/Users/$username/AppData/Roaming/.freeshell"
val fshLinuxExists = freeshellLinuxPath.exists()
val fshWindowsExists = freeshellWindowsPath.exists()
var fpmIsEnabled = true;
fun main(args: Array<String>){
    if (osname.startsWith("Windows")){
        printConsoleWarning("Windows commands are not well supported")
    }
    if ("-d" in args && args.isNotEmpty()) {
        println("DEBUG MODE ENABLED.")
        println(username)
        println(osname)
        println(freeshellLinuxPath)
        println(freeshellWindowsPath)
        println(freeshellLinuxPath.exists())

    }

    if (!fshLinuxExists || !freeshellLinuxPath.isDirectory && !fshWindowsExists || !freeshellWindowsPath.isDirectory) {
        println("${yellow("!:")} Freeshell Config not found. Continuing with setup.")
        Thread.sleep(2000)
        terminal.println("Freeshell Setup", Whitespace.NORMAL, TextAlign.CENTER, OverflowWrap.NORMAL)
        println("---")
        if (osname.startsWith("Windows")) {
            freeshellWindowsPath.mkdir();
        } else {
            freeshellLinuxPath.mkdir()
        }
        val response = questionSystem("Do you want to install FPM?", listOf("yes", "no"))
        if (response == "yes") {
            terminal.println(bold("Freeshell Setup. FPM"))
            terminal.prompt("${blue("?:")} Choose your fpm version (check https://github.com/project-novagon/fpm/releases/latest for the latest release)", choices= listOf("latest", "choose"))
            printConsoleInfo("Installing FPM...", false)
            /*

                Fuel.get("https://github.com/project-novagon/fpm/releases/download/v$version/fpm.py")
                    .response { request, response, result ->
                        val (bytes, error) = result
                        if (bytes != null) {
                            File("path/goes/here").writeBytes(bytes)
                        }
                    }
            */
            printConsoleInfo("FPM Installed!", true)

        }
        else {
            terminal.println("${green("i:")} ${white("Skipping FPM setup")}")
        }
        //TODO: make the rest of the setup
        //curl -OJLs https://github.com/project-novagon/fpm/releases/download/v1.2.0/fpm.py
    } else {
        while (true) {
            print(cursor)
            val input = readln().split(" +".toRegex())

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
                        "help"  -> help(version)
                        "about" -> about(version)
                    } 
                } catch (e: Exception) {
                    help(version)
                }
            }

            "cd" -> {
                try {
                    dir = command[1]
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} }"
                } catch (e: Exception) {
                    dir = homedir 
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} }"
                }
            }

            "exit" -> exitProcess(0)
        }
    } else {
        try {
            command.joinToString(separator=" ").run(File(dir))
        } catch (e: Exception) {
            printConsoleError("FS01", "${command.joinToString(separator=" ")} not found. type \"fs help\" for help")
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
