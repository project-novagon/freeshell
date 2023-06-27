package api.fpm
fun fpm(subcommand: List<String>){
    when(subcommand[0]){
        "about"   -> aboutFPM()
    }
}

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
    println("by dvnlx, mkukiro")

}