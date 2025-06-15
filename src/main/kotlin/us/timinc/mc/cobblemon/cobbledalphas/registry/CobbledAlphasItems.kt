package us.timinc.mc.cobblemon.cobbledalphas.registry

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.modResource
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.Balm
import us.timinc.mc.cobblemon.cobbledalphas.item.BalmItem

object CobbledAlphasItems {
    @Suppress("unused")
    val GOOEY_BALM = registerBalm("gooey_balm", CobbledAlphasBalms.GOOEY_BALM)
    @Suppress("unused")
    val DAZE_BALM = registerBalm("daze_balm", CobbledAlphasBalms.DAZE_BALM)
    @Suppress("unused")
    val STRONG_BALM = registerBalm("strong_balm", CobbledAlphasBalms.STRONG_BALM)
    @Suppress("unused")
    val REGULAR_BALM = registerBalm("regular_balm", CobbledAlphasBalms.REGULAR_BALM)

    @Suppress("unused")
    val DUMMY_BALM = registerBalm("dummy_balm", CobbledAlphasBalms.DUMMY_BALM)

    private fun registerBalm(id: String, balm: Balm): BalmItem {
        val item = register(id, BalmItem(balm))
        balm.item = item
        return item
    }

    private fun <T : Item> register(id: String, item: T): T =
        Registry.register(BuiltInRegistries.ITEM, modResource(id), item)
}