package io.whyzervellasskx.simplybroadcast

import com.google.inject.Inject
import io.whyzervellasskx.simplybroadcast.model.SmartLifoCompositeTerminable
import io.whyzervellasskx.simplybroadcast.service.CommandService
import io.whyzervellasskx.simplybroadcast.service.ConfigurationService
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.plugin.Plugin
import org.slf4j.Logger

class PluginEntryPoint @Inject constructor(
    private val bukkitAudiences: BukkitAudiences,
    private val commandService: CommandService,
    private val configurationService: ConfigurationService,
    logger: Logger,

    ) {

    private val terminableRegistry = SmartLifoCompositeTerminable(logger)

    fun start() {

        audiences = bukkitAudiences

        terminableRegistry.apply {
            with(bukkitAudiences)
            bindModule(commandService)
            bindModule(configurationService)
        }
    }

    fun stop() {
        terminableRegistry.closeAndReportException()
    }

    companion object {
        lateinit var audiences: BukkitAudiences
    }
}
