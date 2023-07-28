package api.setup.updater.fpm

import api.printConsoleInfo
import api.printConsoleWarning
import api.setup.setupStarter.*
import com.github.ajalt.mordant.rendering.TextStyles
import okhttp3.Request
import java.io.File
import java.io.IOException

fun fpmSetup(){
    terminal.println(TextStyles.bold("Freeshell Setup #2; FPM"))
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
    }
    printConsoleInfo("Changing Permissions for FPM...", false)
    val fpmPath = File(if (osname.startsWith("Windows")) "$fpmWindowsPath/fpm.py" else "$fpmLinuxPath/fpm.py")
    fpmPath.setExecutable(true)
    fpmPath.setReadable(true)
    printConsoleInfo("Changed Permissions", true)

}