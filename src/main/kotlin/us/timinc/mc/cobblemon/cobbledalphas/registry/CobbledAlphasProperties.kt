package us.timinc.mc.cobblemon.cobbledalphas.registry

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import us.timinc.mc.cobblemon.cobbledalphas.customproperties.AlphaProperty
import us.timinc.mc.cobblemon.cobbledalphas.customproperties.PacifiedProperty

object CobbledAlphasProperties {
    val ALPHA = AlphaProperty()
    val PACIFIED = PacifiedProperty()

    fun register() {
        CustomPokemonProperty.register(ALPHA)
        CustomPokemonProperty.register(PACIFIED)
    }
}