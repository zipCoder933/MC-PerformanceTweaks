package alternate.current;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import alternate.current.command.AlternateCurrentCommand;
import alternate.current.util.profiler.ACProfiler;
import alternate.current.util.profiler.Profiler;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.zipcoder.performanceTweaks.PerfTweaks;

/**
 * Taken from
 * https://github.com/SpaceWalkerRS/alternate-current/tree/forge
 */
public class AlternateCurrentMod {
//	public static final String MOD_ID = "alternate_current";
//	public static final String MOD_VERSION = "1.7.0";

	public static final String MOD_NAME = "Alternate Current";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
	public static final boolean DEBUG = false;
	public static boolean on = true;
	public static Profiler createProfiler() {
		return DEBUG ? new ACProfiler() : Profiler.DUMMY;
	}

	@EventBusSubscriber(modid = PerfTweaks.MODID)
	public static class ModEvents {
		@SubscribeEvent
		public static void onRegisterCommands(RegisterCommandsEvent event) {
			AlternateCurrentCommand.register(event.getDispatcher());
		}
	}
}
