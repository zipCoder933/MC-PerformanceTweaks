package org.lightning323.perftweaks.optimizations.despawn;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.lightning323.perftweaks.Performancetweaks;
import org.lightning323.perftweaks.config.ConfigManager;
import org.lightning323.perftweaks.config.ModConfig;

public class MobMixinUtils {

    public static void dropEquipmentOnPickup(Mob entity) {
        if (ConfigManager.INSTANCE.dropItemsOfDespawnedMobs) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
                if (!itemStack.isEmpty()) {
                    // create a copy of their items and drop it
                    ItemStack dropStack = itemStack.copy();
                    entity.spawnAtLocation(dropStack);
                }
            }
        }
    }

    public static boolean matchesStackedName(String customName, net.minecraft.world.entity.Entity entity) {
        return java.util.regex.Pattern.compile(
                java.util.regex.Pattern.quote(getLocalizedEntityName(entity.getType()).getString()) + " x\\d+"
                )
                .matcher(customName).find();
    }

    public static net.minecraft.network.chat.Component getLocalizedEntityName(net.minecraft.world.entity.EntityType<?> entityType) {
        String translationKey = entityType.getDescriptionId();
        return net.minecraft.network.chat.Component.translatable(translationKey);
    }
}
