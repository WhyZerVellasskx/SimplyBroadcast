package io.whyzervellasskx.simplybroadcast.bootstrap

import com.google.inject.AbstractModule
import com.google.inject.Provides
import jakarta.inject.Singleton
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.slf4j.Logger

class InjectionModule(
    private val plugin: Plugin
) : AbstractModule() {

    override fun configure() {
        bind(Plugin::class.java).toInstance(plugin)

    }

    @Singleton
    @Provides
    fun BukkitAudiences(plugin: Plugin): BukkitAudiences = BukkitAudiences.create(plugin)

    @Provides
    fun MiniMessage(): MiniMessage = MiniMessage.miniMessage()

    @Provides
    fun Server(plugin: Plugin): Server = plugin.server

    @Provides
    fun Logger(plugin: Plugin): Logger = plugin.slF4JLogger
}
