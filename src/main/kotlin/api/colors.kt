enum class ANSIHeaders(private val Code: String) {
    RESET("\u001B[0m"),
    BOLD("\u001B[1m"),
    FAINT("\u001B[2m"),
    ITALIC("\u001B[3m"),
    UNDERLINE("\u001B[4m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    override fun toString(): String {
        return Code
    }
}
