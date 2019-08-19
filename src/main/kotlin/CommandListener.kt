import kotlinx.coroutines.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.lang.Exception

val commandRegex = Regex("^!(\\w)+")

fun sendMessage(channel: MessageChannel, message: String): CompletableDeferred<Message> {
    val defer = CompletableDeferred<Message>()
    channel.sendMessage(message).queue { res -> defer.complete(res) }
    return defer
}

class CommandListener : ListenerAdapter() {
    @ExperimentalCoroutinesApi
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val msg = event.message
        val prefix = commandRegex.find(msg.contentRaw)
        val channel = event.channel

        if (prefix == null || prefix.value.isEmpty()) {
            return
        }

        when (prefix.value) {
            "!status" -> GlobalScope.launch {
                // TODO fetch data here
                val data: Deferred<Int> = async {
                    delay(300)
                    return@async (Math.random() * 100).toInt()
                }
                val message = sendMessage(channel, "getting server status..")
                try {
                    withTimeout(5000) {
                        message.await().editMessage(data.await().toString()).queue()
                    }
                } catch (e: TimeoutCancellationException) {
                    if (message.isCompleted) {
                        message.getCompleted().editMessage("took too long").queue()
                    }
                    println("!status took too long -- job cancelled")
                } catch (e: Exception) {
                    System.err.println(e.message)
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