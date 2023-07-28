package api.setup.setupStarter

import api.printConsoleInfo
import api.printConsoleWarning
import api.questionSystem
import api.setup.updater.fpm.fpmSetup
import com.github.ajalt.mordant.rendering.*
import com.github.ajalt.mordant.terminal.Terminal
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
val okHttpClient = OkHttpClient()
val terminal = Terminal()
var username = System.getProperty("user.name")
var osname = System.getProperty("os.name")
val freeshellLinuxPath = File("/home/$username/.local/share/.freeshell")
val fpmLinuxPath = File("/home/$username/.local/share/.freeshell/fpm")
val freeshellWindowsPath = File("C:/Users/$username/AppData/Roaming/.freeshell")
val fpmWindowsPath = File("C:/Users/$username/AppData/Roaming/.freeshell")

fun setupInit(){
    println("${TextColors.yellow("!:")} Freeshell Config not found. Continuing with setup.")
    Thread.sleep(2000)
    terminal.println("Freeshell Setup", Whitespace.NORMAL, TextAlign.CENTER, OverflowWrap.NORMAL)
    println("---")
    if (osname.startsWith("Windows")) {
        freeshellWindowsPath.mkdir()
    } else {
        freeshellLinuxPath.mkdir()
    }
    setLanguage()
    val installFPM = questionSystem("Do you want to install FPM?", listOf("yes", "no"))
    if (installFPM == "yes") {
       fpmSetup()
    }
    else {
        printConsoleInfo("Skipping FPM", false)
    }
}
