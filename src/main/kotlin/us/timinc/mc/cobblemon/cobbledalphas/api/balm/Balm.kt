package us.timinc.mc.cobblemon.cobbledalphas.api.balm

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import us.timinc.mc.cobblemon.cobbledalphas.item.BalmItem
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasProperties.PACIFIED

open class Balm(
    val strength: Int,
) {
    lateinit var item: BalmItem

    fun item(): BalmItem = item

    open fun affectHit(pokemonEntity: PokemonEntity) {
        PACIFIED.addToEntity(pokemonEntity, strength)
    }
}