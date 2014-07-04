package killbait.starterkits;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import killbait.starterkits.common.handlers.ConfigHandler;
import killbait.starterkits.common.handlers.EntityEventHandler;
import killbait.starterkits.common.handlers.GuiHandler;
import killbait.starterkits.common.item.ItemKitCreator;
import killbait.starterkits.common.network.PacketHandler;
import killbait.starterkits.common.proxy.IProxy;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.item.Item;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION)

public class StarterKits {

    /* create a safe instance of the mod */
    @Mod.Instance(Reference.MOD_ID)
    public static StarterKits instance;

    /* Resister our proxy classes */

    @SidedProxy(clientSide = "killbait.starterkits.common.proxy.ClientProxy", serverSide = "killbait.starterkits.common.proxy.ServerProxy")
    public static IProxy proxy;

    /* Define our items*/

    public static Item kitCreator;
    //public static ArrayList ArrayList<String> permissionList = new ArrayList<String>();

    public static SimpleNetworkWrapper networkWrapper;

    /* Register the preInit Handler */

    @Mod.EventHandler
    public void  preInit(FMLPreInitializationEvent event)
    {
        // Register our items
        LogHelper.info("Registering Items");
        kitCreator = new ItemKitCreator();
        GameRegistry.registerItem(kitCreator,"kitCreator");
        LogHelper.info("Loading Config");
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        LogHelper.info("Registering packet handler");
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());
        networkWrapper.registerMessage(PacketHandler.Handler.class, PacketHandler.class, Reference.GUI_BUTTON_PACKET_ID, Side.CLIENT);
        //networkWrapper.registerMessage(PacketHandler.class, PacketHandler.class, Reference.GUI_BUTTON_PACKET_ID, Side.SERVER);
        LogHelper.info("preInit Finished");

    }

    /* Register the init Handler */

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        // Register the GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        /* Initialize mod tile entities
        proxy.registerTileEntities();*/

        // Initialize custom rendering and pre-load textures (Client only)
        //proxy.initRenderingAndTextures();

        // Register the Items Event Handler
        //proxy.registerEventHandlers();

        FMLCommonHandler.instance().bus().register(new EntityEventHandler());

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
        //FMLCommonHandler.instance().bus().register(new EntityEventHandler());
        //NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());




    }
}
