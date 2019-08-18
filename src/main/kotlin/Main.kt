import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.lang.Exception
import java.util.Properties
import kotlin.system.exitProcess

fun main() {
    try {
        val properties = Properties()
        val propertiesFile = System.getProperty("user.dir") + "\\local.properties"
        val inputStream = FileInputStream(propertiesFile)

        properties.load(inputStream)

        val token = properties.getProperty("token") ?: ""

        JDABuilder(AccountType.BOT)
            .setToken(token)
            .addEventListeners(Bot())
            .build()
    } catch (e: Exception) {
        when (e) {
            is FileNotFoundException -> System.err.println("Could not find local.properties")
            else -> System.err.println(e.message)
        }
        exitProcess(1)
    }
}