//package org.lightning323.platform;
//
//import com.lightning323.platform.services.IPlatformHelper;
//import net.fabricmc.loader.api.FabricLoader;
//
//public class FabricPlatformHelper implements IPlatformHelper {
//
//    @Override
//    public String getPlatformName() {
//        return "Fabric";
//    }
//
//    @Override
//    public boolean isModLoaded(String modId) {
//
//        return FabricLoader.getInstance().isModLoaded(modId);
//    }
//
//    @Override
//    public boolean isDevelopmentEnvironment() {
//
//        return FabricLoader.getInstance().isDevelopmentEnvironment();
//    }
//}
