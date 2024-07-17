package io.whyzervellasskx.simplybroadcast.extensions

import io.whyzervellasskx.simplybroadcast.model.ExtendedAudience
import org.bukkit.command.CommandSender


val CommandSender.adventure: ExtendedAudience
    get() {
        return ExtendedAudience(listOf(this))
    }
