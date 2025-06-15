package us.timinc.mc.cobblemon.cobbledalphas.customproperties

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.properties.IntProperty

class PacifiedProperty : CustomPokemonPropertyType<IntProperty> {
    override val keys: Iterable<String>
        get() = setOf("pacified")
    override val needsKey: Boolean
        get() = true

    override fun examples(): Collection<String> = (0..255).map { it.toString() }

    override fun fromString(value: String?): IntProperty = IntProperty(
        keys.first(),
        value?.toInt() ?: 0,
        ::pokemonApplicator,
        ::entityApplicator,
        ::pokemonMatcher,
        ::entityMatcher
    )

    fun pokemonApplicator(pokemon: Pokemon, value: Int) {
        pokemon.persistentData.putInt(keys.first(), value)
    }

    fun pokemonRetriever(pokemon: Pokemon): Int {
        return if (!pokemon.persistentData.contains(keys.first())) 0
        else pokemon.persistentData.getInt(keys.first())
    }

    fun addToPokemon(pokemon: Pokemon, value: Int) {
        pokemonApplicator(pokemon, value + pokemonRetriever(pokemon))
    }

    fun addToEntity(entity: PokemonEntity, value: Int) {
        addToPokemon(entity.pokemon, value)
    }

    fun entityApplicator(entity: PokemonEntity, value: Int) {
        pokemonApplicator(entity.pokemon, value)
    }

    fun pokemonMatcher(pokemon: Pokemon, value: Int): Boolean =
        pokemon.persistentData.contains(keys.first()) && pokemon.persistentData.getInt(keys.first()) >= value

    fun entityMatcher(entity: PokemonEntity, value: Int): Boolean = pokemonMatcher(entity.pokemon, value)
}