package us.timinc.mc.cobblemon.cobbledalphas.registry

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.modResource
import us.timinc.mc.cobblemon.cobbledalphas.entity.BalmEntity

object CobbledAlphasEntityTypes {
    val BALM_KEY = modResource("balm")
    val BALM: EntityType<BalmEntity> = register(
        BALM_KEY.path, EntityType.Builder.of(
            { _, level -> BalmEntity(level) }, MobCategory.MISC
        ).sized(0.5F, 0.5F).build(BALM_KEY.toString())
    )

    private fun <T : EntityType<*>> register(id: String, entityType: T): T = Registry.register(
        BuiltInRegistries.ENTITY_TYPE, modResource(id), entityType
    )
}