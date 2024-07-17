package io.whyzervellasskx.simplybroadcast.configuration.type

import io.whyzervellasskx.simplybroadcast.extensions.asMiniMessageComponent
import io.whyzervellasskx.simplybroadcast.extensions.parseMiniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class MiniMessageComponent(var rawString: String, parsedComponent: Component) : ComponentLike by parsedComponent {

    fun resolve(vararg tagResolvers: TagResolver): MiniMessageComponent {
        return rawString.parseMiniMessage(*tagResolvers).asMiniMessageComponent()
    }

    override fun toString(): String {
        return "MiniMessageComponent($rawString)"
    }
}
