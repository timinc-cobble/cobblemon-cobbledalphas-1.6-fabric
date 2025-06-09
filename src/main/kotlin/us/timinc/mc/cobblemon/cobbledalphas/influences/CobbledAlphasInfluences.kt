package us.timinc.mc.cobblemon.cobbledalphas.influences

import com.cobblemon.mod.common.api.spawning.spawner.PlayerSpawnerFactory

object CobbledAlphasInfluences {
    fun register() {
        PlayerSpawnerFactory.influenceBuilders.add { AlphaInfluence() }
    }
}