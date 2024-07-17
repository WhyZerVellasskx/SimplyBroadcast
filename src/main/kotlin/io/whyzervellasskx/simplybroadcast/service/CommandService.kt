package io.whyzervellasskx.simplybroadcast.service

import com.google.inject.Inject
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages
import io.whyzervellasskx.simplybroadcast.command.BroadcastCommand
import io.whyzervellasskx.simplybroadcast.command.ReloadCommand
import io.whyzervellasskx.simplybroadcast.extensions.parseMiniMessage
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.plugin.Plugin

class CommandService @Inject constructor(
    private val broadcastCommand: BroadcastCommand,
    private val reloadCommand: ReloadCommand,
    private val configurationService: ConfigurationService,
    private val plugin: Plugin

) : TerminableModule {

    private val config get() = configurationService

    override fun setup(consumer: TerminableConsumer) {
        LiteBukkitFactory.builder(plugin.name, plugin)

            .commands(broadcastCommand, reloadCommand)
            .message(LiteBukkitMessages.INVALID_USAGE) { config.config.message.invalidUsage }
            .message(LiteBukkitMessages.MISSING_PERMISSIONS) { config.config.message.noPermission }
            .build()

    }
}