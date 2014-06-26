package killbait.starterkits;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import killbait.starterkits.common.EntityEventHandler;
import killbait.starterkits.common.item.ItemKitCreator;
import killbait.starterkits.common.proxy.Proxy;
import net.minecraft.item.Item;

@Mod(modid="StarterKits", name="StarterKits", version="1.7.2-0.1")

public class StarterKits {

    /* create a safe instance of the mod */
    @Mod.Instance("StarterKits")
    public static StarterKits instance;

    /* Create the proxies we need
    * TODO:
    * Is this rearly needed ????? Remove if not
    */

    @SidedProxy(clientSide = "killbait.starterkits.proxy.ProxyClient", serverSide = "killbait.starterkits.proxy.ProxyServer")
    public static Proxy proxy;

    /* */
    public static Item kitCreator;

    /* Register the preInit Handler */

    @Mod.EventHandler
    public void  preInit(FMLPreInitializationEvent event)
    {
        kitCreator = new ItemKitCreator();
        GameRegistry.registerItem(kitCreator,"kitCreator");
        FMLLog.info("[StarterKits]: ".concat("preInit finished"));

    }

    /* Register the init Handler */

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    /* Register the postInit Handler */

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        /* We must add our eventhandler class to the event bus or it will never be called.
         * Which way is the correct one? i'm new to modding so i don't fully understand
         * the differences yet
         */

        /* Register our handler using MinecraftForge */
        /*MinecraftForge.EVENT_BUS.register(new EntityEventHandler());*/

        /* Register our handler using FML */
        FMLCommonHandler.instance().bus().register(new EntityEventHandler());




    }
}
