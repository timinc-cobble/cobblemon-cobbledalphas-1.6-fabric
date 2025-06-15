package us.timinc.mc.cobblemon.cobbledalphas.registry

import com.cobblemon.mod.common.item.group.CobblemonItemGroups
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents

object CobbledAlphasCreativeTabs {
    fun registerTabContents() {
        ItemGroupEvents.modifyEntriesEvent(CobblemonItemGroups.UTILITY_ITEMS_KEY).register {
            it.accept(CobbledAlphasItems.REGULAR_BALM)
            it.accept(CobbledAlphasItems.STRONG_BALM)
            it.accept(CobbledAlphasItems.GOOEY_BALM)
            it.accept(CobbledAlphasItems.DAZE_BALM)
        }
    }
}