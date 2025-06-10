package us.timinc.mc.cobblemon.cobbledalphas.recipe

import com.cobblemon.mod.common.api.moves.BenchedMove
import com.cobblemon.mod.common.api.moves.Moves
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.IVs
import com.cobblemon.mod.common.pokemon.Pokemon
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.config
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.modResource
import us.timinc.mc.cobblemon.cobbledalphas.api.gson.getOrNull
import us.timinc.mc.cobblemon.cobbledalphas.customproperties.CobbledAlphasProperties.ALPHA
import kotlin.random.Random.Default.nextFloat

class AlphaRecipe(
    val id: ResourceLocation,
    val matcher: PokemonProperties,
    val guaranteedMaxIvs: Int = 3,
    val levelBoost: Int = 10,
    val specialMoves: List<String> = emptyList(),
    val chance: Float = 100F,
) {
    fun matches(pokemon: Pokemon) = matcher.matches(pokemon)

    fun apply(pokemon: Pokemon) {
        if (!matches(pokemon)) return
        if (nextFloat() * 100F > chance) return

        ALPHA.pokemonApplicator(pokemon, true)

        val ivs = pokemon.ivs
        if (guaranteedMaxIvs > 0) {
            val imperfectIvs = ivs.filter { (_, value) -> value < IVs.MAX_VALUE }
            if (imperfectIvs.isNotEmpty()) {
                val ivsToPerfect = imperfectIvs.shuffled().take(guaranteedMaxIvs)
                for ((stat) in ivsToPerfect) {
                    ivs[stat] = IVs.MAX_VALUE
                }
            }
        }

        pokemon.level += levelBoost

        var replaceIndex = 0
        for (moveName in (if (config.shuffleSpecialMoves) specialMoves.shuffled() else specialMoves)) {
            val move = Moves.getByName(moveName) ?: continue

            if (pokemon.moveSet.hasSpace()) {
                pokemon.moveSet.add(move.create())
            } else if (replaceIndex < 4) {
                pokemon.moveSet.setMove(replaceIndex++, move.create())
            } else {
                val benchedMove = BenchedMove(move, 0)
                if (!pokemon.benchedMoves.contains(benchedMove)) {
                    pokemon.benchedMoves.add(benchedMove)
                }
            }
        }
    }

    object Manager : SimpleJsonResourceReloadListener(Gson(), "alphas"), IdentifiableResourceReloadListener {
        private var recipes: List<AlphaRecipe> = listOf()
        override fun apply(
            objectMap: MutableMap<ResourceLocation, JsonElement>,
            resourceManager: ResourceManager,
            profilerFiller: ProfilerFiller,
        ) {
            recipes = objectMap.entries.map(::parseRecipe)
        }

        private fun parseRecipe(entry: MutableMap.MutableEntry<ResourceLocation, JsonElement>): AlphaRecipe {
            val id = entry.key
            val data = entry.value as JsonObject

            return AlphaRecipe(id,
                data.getOrNull("matcher")?.asString?.let(PokemonProperties::parse)
                    ?: throw Error("Invalid or missing matcher for $id"),
                data.getOrNull("guaranteedMaxIvs")?.asInt ?: 3,
                data.getOrNull("levelBoost")?.asInt ?: 10,
                data.getOrNull("specialMoves")?.asJsonArray?.map { it.asString } ?: emptyList(),
                data.getOrNull("chance")?.asFloat ?: 100F)
        }

        fun findMatching(pokemon: Pokemon) = recipes.find { it.matches(pokemon) }

        override fun getFabricId(): ResourceLocation = modResource("alphas")
    }
}