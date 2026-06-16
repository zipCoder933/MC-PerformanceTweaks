package org.lightning323.perftweaks.config;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModConfig {
    @SerializedName("redstone.enable_by_default")
    public boolean enableAlternateCurrentByDefault = true;

    @SerializedName("despawn.mob_names_to_persist")
    public Set<String> persistentMobs = new HashSet<>(List.of("corpse:corpse"));

    @SerializedName("dewpawn.drop_items_of_despawned_mobs")
    public boolean dropItemsOfDespawnedMobs = false;

}