package us.timinc.mc.cobblemon.cobbledalphas

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import us.timinc.mc.cobblemon.cobbledalphas.client.BalmRenderer
import us.timinc.mc.cobblemon.cobbledalphas.network.CobbledAlphasNetworkManager
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasCreativeTabs
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasEntityTypes

object CobbledAlphasClientMod : ClientModInitializer {
    override fun onInitializeClient() {
//        EntityModelLayerRegistry.registerModelLayer(BalmModel.LAYER, BalmModel::createBodyLayer)
        EntityRendererRegistry.register(CobbledAlphasEntityTypes.BALM, ::BalmRenderer)
        CobbledAlphasNetworkManager.registerClientHandlers()
        CobbledAlphasCreativeTabs.registerTabContents()
    }
}