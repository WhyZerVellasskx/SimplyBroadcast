package io.whyzervellasskx.simplybroadcast.bootstrap

import com.google.inject.Guice
import com.google.inject.Stage
import io.whyzervellasskx.simplybroadcast.PluginEntryPoint
import me.lucko.helper.plugin.ExtendedJavaPlugin

class Bootstrap : ExtendedJavaPlugin() {

    private var disabled: Boolean = false

    private lateinit var entryPoint: PluginEntryPoint

    override fun enable() {
        try {
            entryPoint = Guice.createInjector(Stage.PRODUCTION, InjectionModule(this)).getInstance(PluginEntryPoint::class.java)
            entryPoint.start()
        } catch (e: Throwable) {
            slF4JLogger.error("Failed to enable", e)
            server.scheduler.runTask(this, Runnable { server.pluginManager.disablePlugin(this) })
        }
    }

    override fun disable() {
        if (::entryPoint.isInitialized && !disabled) {
            disabled = true
            entryPoint.stop()
        }
    }
}

