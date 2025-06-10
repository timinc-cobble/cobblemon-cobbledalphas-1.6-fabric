package us.timinc.mc.cobblemon.cobbledalphas.event.handlers

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.pokeball.ThrownPokeballHitEvent
import net.minecraft.server.level.ServerPlayer
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.config
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasComponents
import kotlin.math.max

object BlockCaptureOnLowObedience : AbstractEventHandler<ThrownPokeballHitEvent>() {
    override fun handle(e: ThrownPokeballHitEvent) {
        if (!config.blockCaptureOnLowObedience) return
        val player = e.pokeBall.owner as? ServerPlayer ?: return
        val pokemonEntity = e.pokemon

        val playerTeam = Cobblemon.storage.getParty(player)
        val occupiedSlots = playerTeam.getFirstAvailablePosition()?.slot?.let { max(1, it) } ?: 1
        if (occupiedSlots == 0) {
            player.sendSystemMessage(CobbledAlphasComponents.obedienceCaptureFailure(pokemonEntity.pokemon))
            e.cancel()
        }

        val obedienceLevel =
            playerTeam.fold(0) { acc, pokemon -> acc + pokemon.level } / occupiedSlots

        if (pokemonEntity.pokemon.level > obedienceLevel) {
            player.sendSystemMessage(CobbledAlphasComponents.obedienceCaptureFailure(pokemonEntity.pokemon))
            e.cancel()
        }
    }
}