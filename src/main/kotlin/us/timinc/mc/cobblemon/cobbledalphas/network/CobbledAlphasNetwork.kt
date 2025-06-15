package us.timinc.mc.cobblemon.cobbledalphas.network

import com.cobblemon.mod.common.client.net.spawn.SpawnExtraDataEntityHandler
import com.cobblemon.mod.common.net.PacketRegisterInfo

object CobbledAlphasNetwork {
    val s2cPayloads = listOf(
        PacketRegisterInfo(SpawnBalmPacket.ID, SpawnBalmPacket::decode, SpawnExtraDataEntityHandler())
    )
}