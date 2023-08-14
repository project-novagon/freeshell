import api.printConsoleError
import api.setup.setupStarter.setupInit
import com.github.ajalt.mordant.markdown.Markdown
import com.github.ajalt.mordant.rendering.TextColors.cyan
import com.github.ajalt.mordant.rendering.TextColors.green
import com.github.ajalt.mordant.terminal.Terminal
import commands.about
import commands.help
import okhttp3.OkHttpClient
import java.io.File
import java.lang.ProcessBuilder.Redirect
import java.net.InetAddress
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

val okHttpClient = OkHttpClient()
val terminal = Terminal()
var version = "v3.0.0.2"
var username = System.getProperty("user.name")
var osname = System.getProperty("os.name")
var computername = InetAddress.getLocalHost().hostName
var dir = System.getProperty("user.dir")
var homedir = System.getProperty("user.home")
var cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} "
var shellCommands = arrayOf("fs", "exit", "cd", "fpm")
val freeshellLinuxPath = File("/home/$username/.local/share/.freeshell")
val fpmLinuxPath = File("/home/$username/.local/share/.freeshell/fpm")
val freeshellWindowsPath = File("C:/Users/$username/AppData/Roaming/.freeshell")
val fpmWindowsPath = File("C:/Users/$username/AppData/Roaming/.freeshell")

val fshLinuxExists = freeshellLinuxPath.exists()
val fshWindowsExists = freeshellWindowsPath.exists()
fun main(args: Array<String>){
    if (osname.startsWith("Windows")){
        printConsoleError("FS03", "Cant run freeshell on windows! exiting now")
        exitProcess(0)
    }
    if ("-d" in args && args.isNotEmpty()) {
        println("DEBUG MODE ENABLED.")
        println(username)
        println(osname)
        println(freeshellLinuxPath)
        println(freeshellWindowsPath)
        println(freeshellLinuxPath.exists())

    }

    if (!fshLinuxExists && !freeshellLinuxPath.isDirectory && !fshWindowsExists && !freeshellWindowsPath.isDirectory) {
        setupInit()
        //TODO: make the rest of the setup
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
                        "gh"    -> {
                            when(command[2]) {
                                "readme" -> {
                                    terminal.print(Markdown("# Readme Reader (freeshell)"))
                                }
                            }
                        }
                    } 
                } catch (e: Exception) {
                    help(version)
                }
            }

            "cd" -> {
                try {
                    dir = command[1]
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} "
                } catch (e: Exception) {
                    dir = homedir 
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} "
                }
            }

            "fpm" -> {
                when(command[1]){
                    "run" -> {
                        val process = ProcessBuilder(if (osname.startsWith("Windows")) "$fpmWindowsPath/fpm.py" else "$fpmLinuxPath/fpm.py").start()
                    }
                    "update" -> {
                        TODO("We're working on the update system!")
                    }
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