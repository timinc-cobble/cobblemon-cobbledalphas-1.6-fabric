@file:Suppress("MemberVisibilityCanBePrivate")

package us.timinc.mc.cobblemon.cobbledalphas.api.balm

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.core.Holder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance

class StatusBalm(strength: Int, val effect: Holder<MobEffect>, val duration: Int, val amplifier: Int) : Balm(strength) {
    override fun affectHit(pokemonEntity: PokemonEntity) {
        super.affectHit(pokemonEntity)
        pokemonEntity.addEffect(MobEffectInstance(effect, duration, amplifier))
    }
}