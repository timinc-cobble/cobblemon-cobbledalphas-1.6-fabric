package us.timinc.mc.cobblemon.cobbledalphas

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.server.packs.PackType
import us.timinc.mc.cobblemon.cobbledalphas.api.fabric.FabricMod
import us.timinc.mc.cobblemon.cobbledalphas.config.CobbledAlphasConfig
import us.timinc.mc.cobblemon.cobbledalphas.customproperties.CobbledAlphasProperties
import us.timinc.mc.cobblemon.cobbledalphas.influences.CobbledAlphasInfluences
import us.timinc.mc.cobblemon.cobbledalphas.recipe.AlphaRecipe

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
    }
}