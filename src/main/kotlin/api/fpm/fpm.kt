package api.fpm

import ANSIHeaders

fun aboutFPM() {
    println("'########:'########::'##::::'##:\n" +
            " ##.....:: ##.... ##: ###::'###:\n" +
            " ##::::::: ##:::: ##: ####'####:\n" +
            " ######::: ########:: ## ### ##:\n" +
            " ##...:::: ##.....::: ##. #: ##:\n" +
            " ##::::::: ##:::::::: ##:.:: ##:\n" +
            " ##::::::: ##:::::::: ##:::: ##:\n" +
            "..::::::::..:::::::::..:::::..::")
    println("Freeshell Plugin Manager")
    println("by dvnlx, NotHavocc")

}

fun listCommands() {
    println("${ANSIHeaders.BOLD}Commands:${ANSIHeaders.RESET}")
    println("about: opens about page")
    println("install -p/-t packageName: installs package. -p: package, -t: theme")
    println("search -p/-t packageName: searches packages. -p: package, -t: theme")
    println("${ANSIHeaders.YELLOW}! WARN: WORK IN PROGRESS${ANSIHeaders.RESET}")
}