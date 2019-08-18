import kotlinx.coroutines.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

val commandRegex = Regex("^!(\\w)+")

class CommandListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val msg = event.message
        val prefix = commandRegex.find(msg.contentRaw)

        if (prefix == null || prefix.value.isEmpty()) {
            return
        }

        val channel = event.channel

        when (prefix.value) {
            "!status" -> GlobalScope.launch {
                // TODO fetch data here
                var dataDeferred: Deferred<Int> = async {
                    delay(3000)
                    return@async 123
                }
                println(dataDeferred)

                channel.sendMessage("getting server status..")
                    .queue { message: Message -> launch {
                            val data: Int = dataDeferred.await()
                            println(data)
                            message.editMessage("done").queue()
                        }
                    }
            }
            "!ping" -> {
                val time = System.currentTimeMillis()
                channel.sendMessage("Pong!")
                    .queue { response ->
                        response.editMessageFormat(
                            "Pong: %d ms",
                            System.currentTimeMillis() - time
                        ).queue()
                    }
            }
        }

    }
}