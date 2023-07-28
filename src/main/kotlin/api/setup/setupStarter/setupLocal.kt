package api.setup.setupStarter

import api.printConsoleInfo
import api.questionSystem
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import java.util.*

val currentLanguage = Locale.getDefault().displayLanguage
fun setLanguage(){
    terminal.println(TextStyles.bold("Freeshell Setup #1; Language"))
    printConsoleInfo("Auto: System Language", false)
    var language = questionSystem("what language do you choose", listOf("auto", "Italiano", "English", "Deutsch", "Français", "Español"))
    terminal.println("You Chose: ${(if (language == "auto") currentLanguage else language)?.let { TextColors.green(it) }}")
}