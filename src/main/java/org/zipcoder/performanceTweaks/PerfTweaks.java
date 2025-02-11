package org.zipcoder.performanceTweaks;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(org.zipcoder.performanceTweaks.PerfTweaks.MODID)
public class PerfTweaks {
    public static final String MODID = "perf_tweaks";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PerfTweaks() {
        /**
         * Events
         */
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        /**
         * Register the config
         */
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        /**
         * Our mod is only required on the client side
         */
        ModLoadingContext.get().registerExtensionPoint(
                IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true)
        );
    }

//    private void commonSetup(final FMLCommonSetupEvent event) {}

//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {}
//
//    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {
//
//        }
//    }
}
