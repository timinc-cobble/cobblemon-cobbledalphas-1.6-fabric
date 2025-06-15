package us.timinc.mc.cobblemon.cobbledalphas.item

import com.cobblemon.mod.common.util.isServerSide
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import us.timinc.mc.cobblemon.cobbledalphas.api.balm.Balm
import us.timinc.mc.cobblemon.cobbledalphas.entity.BalmEntity


class BalmItem(private val balm: Balm) : Item(Properties().stacksTo(16)) {
    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand,
    ): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(interactionHand)
        level.playSound(
            null,
            player.x,
            player.y,
            player.z,
            SoundEvents.SNOWBALL_THROW,
            SoundSource.NEUTRAL,
            0.5F,
            0.4F / (level.getRandom().nextFloat() * 0.4f + 0.8f)
        )

        if (level.isServerSide()) {
            val balmEntity = BalmEntity(level)
            balmEntity.balm = balm
            balmEntity.owner = player
            balmEntity.setPos(player.position().add(0.0, 1.5, 0.0))
            balmEntity.shootFromRotation(
                player, player.xRot, player.yRot, 0.0F, 1.5F, 1.0F
            )
            level.addFreshEntity(balmEntity)
        }

        player.awardStat(Stats.ITEM_USED.get(this))
        if (!player.isCreative) {
            stack.shrink(1)
        }

        return InteractionResultHolder.sidedSuccess(
            stack, level.isClientSide
        )
    }
}