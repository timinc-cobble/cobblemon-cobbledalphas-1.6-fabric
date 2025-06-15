package us.timinc.mc.cobblemon.cobbledalphas.network

import com.cobblemon.mod.common.net.messages.client.spawn.SpawnExtraDataEntityPacket
import com.cobblemon.mod.common.util.readString
import com.cobblemon.mod.common.util.writeString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import us.timinc.mc.cobblemon.cobbledalphas.CobbledAlphasMod.modResource
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.Balm
import us.timinc.mc.cobblemon.cobbledalphas.entity.BalmEntity
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasBalms

class SpawnBalmPacket(
    val balm: Balm,
    vanillaSpawnPacket: ClientboundAddEntityPacket,
) : SpawnExtraDataEntityPacket<SpawnBalmPacket, BalmEntity>(vanillaSpawnPacket) {
    companion object {
        val ID = modResource("spawn_balm_entity")
        fun decode(buffer: RegistryFriendlyByteBuf): SpawnBalmPacket {
            val balm = CobbledAlphasBalms.BALMS.get(ResourceLocation.parse(buffer.readString()))
            val vanillaPacket = decodeVanillaPacket(buffer)

            return SpawnBalmPacket(balm, vanillaPacket)
        }
    }

    override val id: ResourceLocation = ID

    override fun encodeEntityData(buffer: RegistryFriendlyByteBuf) {
        buffer.writeString(CobbledAlphasBalms.BALMS.getKey(balm).toString())
    }

    override fun applyData(entity: BalmEntity) {
        entity.balm = balm
    }

    override fun checkType(entity: Entity): Boolean =
        entity is BalmEntity
}