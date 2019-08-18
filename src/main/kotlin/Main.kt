import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import java.io.FileInputStream
import java.util.Properties
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

fun main() {
    val properties = Properties()
    val propertiesFile = System.getProperty("user.dir") + "\\local.properties"
    val inputStream = FileInputStream(propertiesFile)

    properties.load(inputStream)

    val token = properties.getProperty("token") ?: ""

    try {
        JDABuilder(AccountType.BOT)
            .setToken(token)
            .addEventListeners(Bot())
            .build()
    } catch (e: LoginException) {
        System.err.println(e.message)
        exitProcess(1)
    }
}