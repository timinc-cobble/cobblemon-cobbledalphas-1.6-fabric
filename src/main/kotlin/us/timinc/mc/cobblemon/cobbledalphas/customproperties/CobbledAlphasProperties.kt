package us.timinc.mc.cobblemon.cobbledalphas.customproperties

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty

object CobbledAlphasProperties {
    val ALPHA = AlphaProperty()

    fun register() {
        CustomPokemonProperty.register(ALPHA)
    }
}