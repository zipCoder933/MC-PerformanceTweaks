package org.lightning323.perftweaks.optimizations.despawn.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MobCategory;
import org.lightning323.perftweaks.optimizations.despawn.Despawn;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class LetMeDespawnCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return literal("despawn")
                .requires(source -> source.hasPermission(2))
                .then(literal("add")
                        .then(argument("mobName", StringArgumentType.greedyString())
                                .suggests(LetMeDespawnCommands::suggestMobNames)
                                .executes(LetMeDespawnCommands::addMob)))
                .then(literal("remove")
                        .then(argument("mobName", StringArgumentType.greedyString())
                                .suggests(LetMeDespawnCommands::suggestConfiguredMobNames)
                                .executes(LetMeDespawnCommands::removeMob)));
    }

    private static CompletableFuture<Suggestions> suggestMobNames(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
            if (entityType.getCategory().equals(MobCategory.MONSTER)) {
                builder.suggest(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
            }
        });
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestConfiguredMobNames(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        Despawn.getMobNames().stream()
                .filter(mobName -> mobName.startsWith(builder.getRemaining()))
                .forEach(builder::suggest);
        return builder.buildFuture();
    }

    private static int addMob(CommandContext<CommandSourceStack> context) {
        String mobName = StringArgumentType.getString(context, "mobName");
        if (Despawn.getMobNames().contains(mobName)) {
            context.getSource().sendSuccess(() -> Component.literal("Mob '" + mobName + "' is already in the configuration.").withStyle(ChatFormatting.RED), false);
        } else {
            Despawn.addMobName(mobName);
            context.getSource().sendSuccess(() -> Component.literal("Added '" + mobName + "' to configuration.").withStyle(ChatFormatting.AQUA), true);
        }
        return 1;
    }

    private static int removeMob(CommandContext<CommandSourceStack> context) {
        String mobName = StringArgumentType.getString(context, "mobName");
        if (Despawn.getMobNames().contains(mobName)) {
            Despawn.removeMobName(mobName);
            context.getSource().sendSuccess(() -> Component.literal("Removed '" + mobName + "' from configuration.").withStyle(ChatFormatting.GOLD), true);
        } else {
            context.getSource().sendSuccess(() -> Component.literal("Mob '" + mobName + "' is not in the configuration.").withStyle(ChatFormatting.RED), false);
        }
        return 1;
    }
}