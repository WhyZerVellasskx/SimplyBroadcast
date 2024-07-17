package io.whyzervellasskx.simplybroadcast.service

import com.google.inject.Inject
import com.google.inject.Singleton
import io.whyzervellasskx.simplybroadcast.configuration.Configuration
import io.whyzervellasskx.simplybroadcast.configuration.serializer.BossBarConfigurationSerializer
import io.whyzervellasskx.simplybroadcast.configuration.serializer.DurationSerializer
import io.whyzervellasskx.simplybroadcast.configuration.serializer.MiniMessageComponentSerializer
import io.whyzervellasskx.simplybroadcast.configuration.type.BossBarConfiguration
import io.whyzervellasskx.simplybroadcast.configuration.type.MiniMessageComponent
import me.lucko.helper.terminable.TerminableConsumer
import me.lucko.helper.terminable.module.TerminableModule
import org.bukkit.plugin.Plugin
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.time.Duration
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

@Singleton
class ConfigurationService @Inject constructor(
    private val plugin: Plugin,
    private val durationSerializer: DurationSerializer,
    private val miniMessageComponentSerializer: MiniMessageComponentSerializer,
    private val bossBarConfigurationSerializer: BossBarConfigurationSerializer,
) : TerminableModule {

    lateinit var config: Configuration
        private set


    private val rootDirectory = Path("")
    private val settingsFile = plugin.dataFolder.toPath().resolve("settings.yml")

    override fun setup(consumer: TerminableConsumer) = doReload()

    fun reload() = doReload()

    fun saveConfig() {
        createLoaderBuilder().path(settingsFile).build().let {
            it.save(it.createNode().set(config))
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal                                                              //
    ///////////////////////////////////////////////////////////////////////////

    @Synchronized
    private fun doReload() {
        plugin.dataFolder.toPath().createDirectories()

        config = createLoaderBuilder().path(settingsFile).build().getAndSave<Configuration>()

    }

    private fun createLoaderBuilder(): YamlConfigurationLoader.Builder {
        return YamlConfigurationLoader.builder()
            .defaultOptions {
                it.serializers { serializers ->
                    serializers
                        .register(MiniMessageComponent::class.java, miniMessageComponentSerializer)
                        .register(Duration::class.java, durationSerializer)
                        .register(BossBarConfiguration::class.java, bossBarConfigurationSerializer)
                        .registerAnnotatedObjects(objectMapperFactory())
                }
            }
            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
    }

    private inline fun <reified T : Any> YamlConfigurationLoader.getAndSave(): T {
        val obj = this.load().get(T::class)!!
        this.save(this.createNode().set(T::class, obj))
        return obj
    }
}