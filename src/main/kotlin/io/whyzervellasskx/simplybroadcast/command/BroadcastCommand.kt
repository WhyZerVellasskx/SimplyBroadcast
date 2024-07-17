package io.whyzervellasskx.simplybroadcast.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.cooldown.Cooldown
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import io.whyzervellasskx.simplybroadcast.extensions.asMiniMessageComponent
import io.whyzervellasskx.simplybroadcast.extensions.parseMiniMessage
import io.whyzervellasskx.simplybroadcast.service.ConfigurationService
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.time.temporal.ChronoUnit

@Command(name = "broadcast", aliases = ["bc"])
class BroadcastCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val config get() = configurationService

    @Permission("simplybroadcast.command.broadcast") //TODO когда-то добавлю задержку на использование, а щас лень
    @Execute
    fun execute(@Context sender: CommandSender, @Arg message: String) {

        Bukkit.getOnlinePlayers().forEach {
            player -> player.sendMessage(config.config.message.broadcastMessage.parseMiniMessage(
            Placeholder.unparsed("player", sender.name),
            Placeholder.unparsed("message", message)
            ))
        }
    }
}