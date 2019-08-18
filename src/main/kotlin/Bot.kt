import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Bot : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val msg = event.message
        if (msg.contentRaw == "!ping") {
            val channel = event.channel
            val time = System.currentTimeMillis()
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                .queue { response /* => Message */ ->
                    response.editMessageFormat(
                        "Pong: %d ms",
                        System.currentTimeMillis() - time
                    ).queue()
                }
        }
    }
}