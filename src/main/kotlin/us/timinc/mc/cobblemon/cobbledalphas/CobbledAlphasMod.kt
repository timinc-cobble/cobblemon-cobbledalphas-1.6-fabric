package us.timinc.mc.cobblemon.cobbledalphas

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.events.CobblemonEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.server.packs.PackType
import us.timinc.mc.cobblemon.cobbledalphas.api.fabric.FabricMod
import us.timinc.mc.cobblemon.cobbledalphas.config.CobbledAlphasConfig
import us.timinc.mc.cobblemon.cobbledalphas.event.handlers.BlockCaptureOnLowObedience
import us.timinc.mc.cobblemon.cobbledalphas.influences.CobbledAlphasInfluences
import us.timinc.mc.cobblemon.cobbledalphas.network.CobbledAlphasNetworkManager
import us.timinc.mc.cobblemon.cobbledalphas.recipe.AlphaRecipe
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasBalms
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasEntityTypes
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasItems
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasProperties

object CobbledAlphasMod : FabricMod<CobbledAlphasConfig>("cobbledalphas", CobbledAlphasConfig::class.java) {
    override fun initialize() {
        var initialized = false
        ServerLifecycleEvents.SERVER_STARTING.register { _ ->
            if (initialized) return@register
            initialized = true
            CobbledAlphasProperties.register()
        }
        CobbledAlphasInfluences.register()
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(AlphaRecipe.Manager)
        CobblemonEvents.THROWN_POKEBALL_HIT.subscribe(Priority.HIGHEST, BlockCaptureOnLowObedience::handle)
        CobbledAlphasBalms
        CobbledAlphasEntityTypes
        CobbledAlphasItems
        CobbledAlphasNetworkManager.registerMessages()
    }
}