package io.whyzervellasskx.simplybroadcast.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.argument.Arg
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import io.github.blackbaroness.fastutilextender.common.map.FastMap
import io.whyzervellasskx.simplybroadcast.extensions.parseMiniMessage
import io.whyzervellasskx.simplybroadcast.service.ConfigurationService
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.time.Duration
import java.time.Instant

@Command(name = "broadcast", aliases = ["bc"])
class BroadcastCommand @Inject constructor(
    private val configurationService: ConfigurationService
) {

    private val config get() = configurationService
    val cooldown = FastMap.newMap<Pair<CommandSender, Duration>, Instant>()

    @Permission("simplybroadcast.command.broadcast")
    @Execute
    fun execute(@Context sender: CommandSender, @Arg message: MutableList<String>) {

        //if (tryBroadcast(sender, Duration.ofSeconds(2))) return

        Bukkit.getOnlinePlayers().forEach {
            player -> player.sendMessage(config.config.message.broadcastMessage.parseMiniMessage(
            Placeholder.unparsed("player", sender.name),
            Placeholder.unparsed("message", message.joinToString(" "))
        ))
        }
    }


    private fun tryBroadcast(sender: CommandSender, duration: Duration) : Boolean {

        val pair = sender to Duration.ofSeconds(3)
        val now = Instant.now()
        val cooldownEndDates = cooldown[pair] ?: Instant.now()

        if (now > cooldownEndDates) {
            return false
        }

        cooldown[pair] = now.plus(config.config.settings.cooldown)

        return true
    }

}