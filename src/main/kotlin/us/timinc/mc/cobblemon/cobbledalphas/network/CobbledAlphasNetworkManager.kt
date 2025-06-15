package us.timinc.mc.cobblemon.cobbledalphas.network

import com.cobblemon.mod.common.NetworkManager
import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.fabric.net.FabricPacketInfo
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer

object CobbledAlphasNetworkManager : NetworkManager {
    override fun sendPacketToPlayer(player: ServerPlayer, packet: NetworkPacket<*>) {
        ServerPlayNetworking.send(player, packet)
    }

    override fun sendToServer(packet: NetworkPacket<*>) {
        ClientPlayNetworking.send(packet)
    }

    fun registerMessages() {
        CobbledAlphasNetwork.s2cPayloads.map {
            FabricPacketInfo(it)
        }.forEach {
            it.registerPacket(client = true)
        }
    }

    fun registerClientHandlers() {
        CobbledAlphasNetwork.s2cPayloads.map {
            FabricPacketInfo(it)
        }.forEach {
            it.registerClientHandler()
        }
    }
}