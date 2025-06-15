package us.timinc.mc.cobblemon.cobbledalphas.entity

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.util.sendParticlesServer
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerEntity
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.Balm
import us.timinc.mc.cobblemon.cobbledalphas.network.SpawnBalmPacket
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasBalms
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasEntityTypes
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasProperties


class BalmEntity(
    level: Level,
    entityType: EntityType<out BalmEntity> = CobbledAlphasEntityTypes.BALM,
) : ThrowableItemProjectile(entityType, level) {
    var balm: Balm = CobbledAlphasBalms.DUMMY_BALM
    var hitEntity: PokemonEntity? = null

    companion object {
        val BALM_TYPE = SynchedEntityData.defineId(BalmEntity::class.java, EntityDataSerializers.STRING)
    }

    override fun getDefaultItem(): Item = balm.item()

    override fun shouldBeSaved(): Boolean = false

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        balm = CobbledAlphasBalms.DUMMY_BALM
        super.defineSynchedData(builder)
        builder.define(BALM_TYPE, CobbledAlphasBalms.BALMS.getId(balm).toString())
    }

    override fun onSyncedDataUpdated(entityDataAccessor: EntityDataAccessor<*>) {
        super.onSyncedDataUpdated(entityDataAccessor)
        if (entityDataAccessor == BALM_TYPE) {
            println(entityData.get(BALM_TYPE))
        }
    }

    private fun playDeathParticles() {
        val level = level() as? ServerLevel ?: return
        val stack = item
        val particleOptions = if (!stack.isEmpty && !stack.`is`(defaultItem)) ItemParticleOption(
            ParticleTypes.ITEM, stack
        ) else ParticleTypes.ITEM_COBWEB
        level.sendParticlesServer(
            particleOptions, position(), 8, Vec3.ZERO, 0.0
        )
    }

    private fun playDeathSound() {
        val level = level() as? ServerLevel ?: return
        level.playSound(
            null,
            x,
            y,
            z,
            if (shouldAffect()) SoundEvents.AMETHYST_CLUSTER_HIT else SoundEvents.AXE_STRIP,
            SoundSource.PLAYERS,
            0.5F,
            0.4F / (level.getRandom().nextFloat() * 0.4f + 0.8f)
        )
    }

    private fun checkSuccess() {
        if (shouldAffect()) {
            handleHit()
        } else {
            handleMiss()
        }
    }

    private fun handleHit() {
        hitEntity?.let { balm.affectHit(it) }
    }

    private fun handleMiss() {
        if ((owner as? ServerPlayer)?.isCreative == false) spawnAtLocation(defaultItem)
    }

    private fun shouldAffect(): Boolean =
        hitEntity?.let { CobbledAlphasProperties.ALPHA.entityMatcher(it, true) } ?: false

    private fun handleHitSomething() {
        playDeathParticles()
        playDeathSound()
        checkSuccess()
        kill()
    }

    override fun onHitEntity(entityHitResult: EntityHitResult) {
        super.onHitEntity(entityHitResult)
        hitEntity = entityHitResult.entity as? PokemonEntity
        handleHitSomething()
    }

    override fun onHitBlock(blockHitResult: BlockHitResult) {
        super.onHitBlock(blockHitResult)
        handleHitSomething()
    }

    override fun getAddEntityPacket(serverEntity: ServerEntity): Packet<ClientGamePacketListener> =
        ClientboundCustomPayloadPacket(
            SpawnBalmPacket(
                this.balm,
                super.getAddEntityPacket(serverEntity) as ClientboundAddEntityPacket
            )
        ) as Packet<ClientGamePacketListener>
}