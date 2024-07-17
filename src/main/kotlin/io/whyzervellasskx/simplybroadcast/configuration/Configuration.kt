package io.whyzervellasskx.simplybroadcast.configuration

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.time.Duration

@ConfigSerializable
data class Configuration(
    val settings: SettingsConfiguration,
    val message: MessageConfiguration,
) {

    @ConfigSerializable
    data class SettingsConfiguration(
        val cooldown: Duration = Duration.ofSeconds(60),
    )

    @ConfigSerializable
    data class MessageConfiguration(
        val noPermission: String = "<#fc4300>У вас недостаточно разрешений",
        val invalidUsage: String = "<#fc4300>Неверное использование команды",
        val cooldown: String = "<#fc4300>Подождите еще <cooldown>",
        val reloadComplete: String = "<#fc4300>Плагин <plugin> перезагружен",
        val broadcastMessage: String = "<color:#cfcfcf>Игрок <#fc4300><player> <color:#cfcfcf>отправил сообщние <#fc4300><message>".trimIndent()
    )

}
