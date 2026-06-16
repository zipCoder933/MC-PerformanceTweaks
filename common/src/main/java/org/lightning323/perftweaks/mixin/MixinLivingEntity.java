package org.lightning323.perftweaks.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.lightning323.perftweaks.optimizations.despawn.MobMixinUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {

    public MixinLivingEntity(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(method = "isInWall", at = @At("HEAD"), cancellable = true)
    private void noClientCheck(CallbackInfoReturnable<Boolean> cir) {
        if(this.level().isClientSide) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "pushEntities", at = @At("HEAD"), cancellable = true)
    private void noPushEntitiesClient(CallbackInfo ci) {
        if (this.level().isClientSide) {
            ci.cancel();
        }
    }


    @Inject(
            method = {"remove"},
            at = @At("HEAD")
    )
    private void letmedespawn$onRemove(CallbackInfo info) {
        //WHEN the entity is removed
        // Hook into the remove method to catch mob removal as this is needed in newer versions & can prevent against lagfixers
        if ((Object) this instanceof Mob entity) {
            MobMixinUtils.dropEquipmentOnPickup(entity);
        }
    }
}