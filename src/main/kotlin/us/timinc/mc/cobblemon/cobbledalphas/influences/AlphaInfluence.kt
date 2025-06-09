package us.timinc.mc.cobblemon.cobbledalphas.influences

import com.cobblemon.mod.common.api.spawning.influence.SpawningInfluence
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.world.entity.Entity
import us.timinc.mc.cobblemon.cobbledalphas.recipe.AlphaRecipe

class AlphaInfluence : SpawningInfluence {
    override fun affectSpawn(entity: Entity) {
        if (entity !is PokemonEntity) return

        AlphaRecipe.Manager.findMatching(entity.pokemon)?.apply(entity.pokemon)
    }
}