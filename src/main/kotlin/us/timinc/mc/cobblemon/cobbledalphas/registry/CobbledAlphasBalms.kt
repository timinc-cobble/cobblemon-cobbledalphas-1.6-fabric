package us.timinc.mc.cobblemon.cobbledalphas.registry

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.fabricmc.fabric.api.event.registry.RegistryAttribute
import net.minecraft.core.DefaultedRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.effect.MobEffects
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.modResource
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.Balm
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.StatusBalm

object CobbledAlphasBalms {
    val BALM_REGISTRY_KEY: ResourceKey<Registry<Balm>> = ResourceKey.createRegistryKey(modResource("balm"))

    val BALMS: DefaultedRegistry<Balm> = FabricRegistryBuilder.createDefaulted(
        BALM_REGISTRY_KEY, modResource("dummy")
    ).attribute(RegistryAttribute.SYNCED).buildAndRegister()

    private fun register(id: String, balm: Balm) = Registry.register(BALMS, modResource(id), balm)

    val GOOEY_BALM: Balm = register("gooey", StatusBalm(5, MobEffects.MOVEMENT_SLOWDOWN, 20 * 15, 1))
    val DAZE_BALM: Balm = register("daze", StatusBalm(5, MobEffects.WEAKNESS, 20 * 15, 1))
    val STRONG_BALM: Balm = register("strong", Balm(10))
    val REGULAR_BALM: Balm = register("regular", Balm(5))

    val DUMMY_BALM: Balm = register("dummy", Balm(0))
}