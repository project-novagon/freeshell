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
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetAddress
import kotlin.system.exitProcess
import java.lang.ProcessBuilder.Redirect
import java.util.concurrent.TimeUnit
import java.io.File
import java.io.IOException

val okHttpClient = OkHttpClient()
val terminal = Terminal()
var version = "v3.0.0.4.od"
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

    if (!fshLinuxExists && !freeshellLinuxPath.isDirectory && !fshWindowsExists && !freeshellWindowsPath.isDirectory) {
        println("${yellow("!:")} Freeshell Config not found. Continuing with setup.")
        Thread.sleep(2000)
        terminal.println("Freeshell Setup", Whitespace.NORMAL, TextAlign.CENTER, OverflowWrap.NORMAL)
        println("---")
        if (osname.startsWith("Windows")) {
            freeshellWindowsPath.mkdir()
        } else {
            freeshellLinuxPath.mkdir()
        }
        val installFPM = questionSystem("Do you want to install FPM?", listOf("yes", "no"))
        if (installFPM == "yes") {
            terminal.println(bold("Freeshell Setup. FPM"))
            if (osname.startsWith("Windows")) fpmWindowsPath.mkdir() else fpmLinuxPath.mkdir()
            printConsoleWarning("Automatically installing latest FPM verison...")

            val request = Request.Builder()
                .url("https://api.github.com/repos/project-novagon/fpm/releases/latest")
                .build()

            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val latestRelease = response.body!!.string()
                val tagName = latestRelease.substringAfter("\"tag_name\":\"").substringBefore("\",\"target_commitish\"")
                println(tagName)

                printConsoleInfo("Installing FPM...", false)
                val fpmRequest = Request.Builder()
                    .url("https://github.com/project-novagon/fpm/releases/download/$tagName/fpm.py")
                    .build()

                val fpmfile = okHttpClient.newCall(fpmRequest).execute()
                File("${if (osname.startsWith("Windows")) "$fpmWindowsPath/fpm.py" else "$fpmLinuxPath/fpm.py"}").writeBytes(fpmfile.body!!.bytes())
                printConsoleInfo("FPM Installed!", true)
            }


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
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} "
                } catch (e: Exception) {
                    dir = homedir 
                    cursor  = "${cyan("($username @ $computername : $dir )")} ${green("~>")} "
                }
            }

            "fpm"  -> {
                
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
