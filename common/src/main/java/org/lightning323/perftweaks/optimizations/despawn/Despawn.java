package org.lightning323.perftweaks.optimizations.despawn;

import net.minecraft.world.entity.Mob;
import org.lightning323.perftweaks.config.ConfigManager;

import java.util.Set;

public final class Despawn {

    public static Set<String> getMobNames() {
        return ConfigManager.INSTANCE.persistentMobs;
    }

    public static void addMobName(String mobName) {
        ConfigManager.INSTANCE.persistentMobs.add(mobName);
        ConfigManager.save();
    }

    public static void removeMobName(String mobName) {
        ConfigManager.INSTANCE.persistentMobs.remove(mobName);
        ConfigManager.save();
    }

    public static boolean hasDespawnableName(Mob entity) {
        if(entity.hasCustomName()) {
            return MobMixinUtils.matchesStackedName(entity.getCustomName().getString(), entity);
        }
        return true;
    }
}
