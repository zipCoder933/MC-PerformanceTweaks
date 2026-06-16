package org.lightning323.perftweaks.mixin.accessor;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mob.class)
public interface MobAccessor {

    @Accessor("persistenceRequired")
    boolean isPersistenceRequired();

    @Accessor("persistenceRequired")
    void setPersistenceRequired(boolean persistenceRequired);
}