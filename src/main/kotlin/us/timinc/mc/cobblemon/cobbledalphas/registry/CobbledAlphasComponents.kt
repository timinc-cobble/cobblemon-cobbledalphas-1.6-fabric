package us.timinc.mc.cobblemon.cobbledalphas.registry

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.id

object CobbledAlphasComponents {
    fun obedienceCaptureFailure(pokemon: Pokemon): MutableComponent =
        quickTranslatable("feedback", "obedience_capture_failure", pokemon.getDisplayName())

    fun quickTranslatable(category: String, msg: String, vararg params: Any): MutableComponent =
        Component.translatable("$id.$category.$msg", *params)
}