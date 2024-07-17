package io.whyzervellasskx.simplybroadcast.command

import com.google.inject.Inject
import dev.rollczi.litecommands.annotations.command.Command
import dev.rollczi.litecommands.annotations.context.Context
import dev.rollczi.litecommands.annotations.execute.Execute
import dev.rollczi.litecommands.annotations.permission.Permission
import io.whyzervellasskx.simplybroadcast.extensions.adventure
import io.whyzervellasskx.simplybroadcast.service.ConfigurationService
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

@Command(name = "simplybroadcast", aliases = ["sbc"])
class ReloadCommand @Inject constructor(
    private val plugin: Plugin,
    private val configurationService: ConfigurationService,
) {
    private val config get() = configurationService

    @Permission("simplybroadcast.command.reload")
    @Execute(name = "reload")
    fun execute(@Context sender: CommandSender) {

        configurationService.reload()
        sender.adventure.sendMessage(config.config.message.reloadComplete,
            Placeholder.unparsed("plugin", plugin.name))

    }
}