@file:Suppress("MemberVisibilityCanBePrivate")

package us.timinc.mc.cobblemon.cobbledalphas.api.fabric

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import us.timinc.mc.cobblemon.cobbledalphas.config.ConfigBuilder

abstract class FabricMod<ConfigType : AbstractConfig>(val id: String, private val configClass: Class<ConfigType>) : ModInitializer {
    var config: ConfigType = ConfigBuilder.load(configClass, id)
    val logger = LogManager.getLogger(id)

    override fun onInitialize() {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { _, _, _ ->
            config = ConfigBuilder.load(configClass, id)
        }
        initialize()
    }

    abstract fun initialize()

    fun modResource(name: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(id, name)

    fun debug(msg: String, bypassConfig: Boolean = false) {
        if (!config.debug && !bypassConfig) return
        logger.info(msg)
    }
}