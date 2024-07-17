package io.whyzervellasskx.simplybroadcast.model

import io.whyzervellasskx.simplybroadcast.PluginEntryPoint
import io.whyzervellasskx.simplybroadcast.configuration.type.TitleConfiguration
import io.whyzervellasskx.simplybroadcast.extensions.legacy
import io.whyzervellasskx.simplybroadcast.extensions.parseMiniMessage
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ExtendedAudience(private val senders: Iterable<CommandSender>) {

    val audience: Audience
        get() {
            return Audience.audience(senders.map { createAudience(it) })
        }

    @Suppress("DEPRECATION")
    fun kick(componentLike: ComponentLike?) {
        val message by lazy { componentLike?.legacy() }

        senders.asSequence()
            .mapNotNull { it as? Player }
            .forEach { it.kickPlayer(message) }
    }

    fun playSound(sound: Sound) {
        senders.asSequence()
            .mapNotNull { it as? Player }
            .forEach { it.playSound(it.location, sound, 1f, 1f) }
    }

    fun sendMessage(message: String, vararg tagResolvers: TagResolver) {
        audience.sendMessage(message.parseMiniMessage(*tagResolvers))
    }

    fun sendMessage(message: ComponentLike) {
        audience.sendMessage(message)
    }

    fun sendActionBar(message: String, vararg tagResolvers: TagResolver) {
        audience.sendActionBar(message.parseMiniMessage(*tagResolvers))
    }

    fun showTitle(title: TitleConfiguration, vararg tagResolvers: TagResolver) {
        audience.showTitle(title.createTitle(*tagResolvers))
    }

    private fun createAudience(sender: CommandSender): Audience {
        return if (sender is Player) {
            PluginEntryPoint.audiences.player(sender)
        } else {
            PluginEntryPoint.audiences.sender(sender)
        }
    }
}
