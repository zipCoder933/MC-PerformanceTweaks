package org.lightning323.perftweaks.optimizations.redstone.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.lightning323.perftweaks.optimizations.redstone.interfaces.mixin.IServerLevel;
import org.lightning323.perftweaks.optimizations.redstone.wire.WireHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(
		method = "saveAllChunks",
		at = @At(
			value = "HEAD"
		)
	)
	private void alternate_current$save(boolean silent, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
		ServerLevel overworld = ((MinecraftServer) (Object) this).overworld();
		WireHandler wireHandler = ((IServerLevel) overworld).alternate_current$getWireHandler();

		wireHandler.getConfig().save(silent);
	}
}
