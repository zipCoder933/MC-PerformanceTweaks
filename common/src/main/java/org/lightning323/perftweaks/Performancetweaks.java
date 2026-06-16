package org.lightning323.perftweaks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lightning323.perftweaks.optimizations.despawn.command.LetMeDespawnCommands;
import org.lightning323.perftweaks.optimizations.redstone.command.AlternateCurrentCommand;
import org.lightning323.perftweaks.optimizations.despawn.LetMeDespawn;

import java.io.File;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public final class Performancetweaks {
    public static final String MOD_ID = "perf_tweaks";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final File LET_ME_DESPAWN_CONFIG_FILE = new File("config/perf_tweaks/despawn.json");

    //common init code
    public static void init() {
        LOGGER.info("Performance Tweaks initialized");
        LetMeDespawn.init();
    }

    //common command registration
    public static void onRegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(Performancetweaks.MOD_ID)
                        .then(AlternateCurrentCommand.build())
                        .then(LetMeDespawnCommands.build())
        );
    }
}
