package us.timinc.mc.cobblemon.cobbledalphas.mixins;

import com.cobblemon.mod.common.entity.pokeball.*;
import com.cobblemon.mod.common.entity.pokemon.*;
import net.minecraft.server.level.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import us.timinc.mc.cobblemon.cobbledalphas.*;

@Mixin(EmptyPokeBallEntity.class)
public abstract class CancelThrownPokeballInBattle extends net.minecraft.world.entity.projectile.ThrowableItemProjectile {
    @org.spongepowered.asm.mixin.Shadow
    @org.spongepowered.asm.mixin.Final
    private static net.minecraft.network.syncher.EntityDataAccessor<Byte> CAPTURE_STATE;

    @org.spongepowered.asm.mixin.Shadow
    protected abstract void drop();

    public CancelThrownPokeballInBattle(net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.ThrowableItemProjectile> entityType, double d, double e, double f, net.minecraft.world.level.Level level) {
        super(entityType, d, e, f, level);
    }

    @Inject(method = "onHitEntity", at = @At(value = "HEAD"), cancellable = true, remap = false)
    void cancelHitEntityInBattle(net.minecraft.world.phys.EntityHitResult hitResult, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (EmptyPokeBallEntity.CaptureState.getEntries().get(this.entityData.get(CAPTURE_STATE).intValue()) == EmptyPokeBallEntity.CaptureState.NOT) {
            if (hitResult.getEntity() instanceof PokemonEntity entity && !this.level().isClientSide) {
                if (entity.isBattling() && CobbledAlphasMod.INSTANCE.getConfig().getBlockCaptureOnLowObedience()) {
                    if (this.getOwner() instanceof ServerPlayer player)
                        player.sendSystemMessage(us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasComponents.INSTANCE.obedienceCaptureFailure(entity.getPokemon()));
                    this.drop();
                    ci.cancel();
                }
            }
        }
    }
}
