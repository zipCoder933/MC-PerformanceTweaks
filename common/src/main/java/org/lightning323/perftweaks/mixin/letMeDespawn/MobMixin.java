package org.lightning323.perftweaks.mixin.letMeDespawn;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lightning323.perftweaks.config.ConfigManager;
import org.lightning323.perftweaks.mixin.accessor.MobAccessor;
import org.lightning323.perftweaks.optimizations.despawn.Despawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            at = {@At("TAIL")},
            method = {"setItemSlotAndDropWhenKilled"}
    )
    //We determine if the mob should have persistence, if yes, the mob will never despawn even if found tools, if no, the mob will despawn no matter what
    private void letmedespawn$setItemSlotAndDropWhenKilled(EquipmentSlot slot, ItemStack stack, CallbackInfo info) {
        Mob entity = (Mob) (Object) this;

//        ItemStack itemStack = entity.getItemBySlot(slot);
//        CustomData component = itemStack.get(DataComponents.CUSTOM_DATA);
//        CompoundTag nbt;
//        if (component != null) {
//            nbt = component.copyTag();
//        } else {
//            nbt = new CompoundTag();
//        }
//        nbt.putBoolean("picked", true);
//        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        String mobName = entity.level().registryAccess().registryOrThrow(Registries.ENTITY_TYPE).getKey(entity.getType()).toString();
        //Persistence required is ONLY TRUE if either:
        //the mob is in the list of persistence required?
        //the mob has no despawnable name (custom name / custom name tag)?
        boolean persistenceRequired = Despawn.getMobNames().contains(mobName) || !Despawn.hasDespawnableName(entity);
        ((MobAccessor) entity).setPersistenceRequired(persistenceRequired);
    }

    @Redirect(
            method = {"checkDespawn"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;discard()V"
            )
    )
    private void letmedespawn$yeetusCheckus(Mob entity) {
        if (ConfigManager.INSTANCE.dropItemsOfDespawnedMobs) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
                if (!itemStack.isEmpty()) {
                    entity.spawnAtLocation(itemStack);
                    entity.setItemSlot(equipmentSlot, ItemStack.EMPTY);
                }
            }
        }
        this.discard();
    }
}