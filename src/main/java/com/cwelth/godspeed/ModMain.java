package com.cwelth.godspeed;

import com.cwelth.godspeed.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.logging.Logger;

@Mod(
        modid = ModMain.MODID,
        name = ModMain.NAME,
        version = ModMain.VERSION,
        acceptableRemoteVersions = "*"
)

public class ModMain {

    public static final String NAME = "Godspeed";
    public static final String MODID = "godspeed";
    public static final String VERSION = "1.1";

    public static final Logger logger = Logger.getLogger(MODID);

    @Mod.Instance("godspeed")
    public static ModMain instance;

    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(clientSide = "com.cwelth.godspeed.proxy.ClientProxy", serverSide = "com.cwelth.godspeed.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        MinecraftForge.EVENT_BUS.register(new EventHandlers());

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
}
