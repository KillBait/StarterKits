package killbait.starterkits.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import killbait.starterkits.StarterKits;
import killbait.starterkits.common.proxy.Proxy;

public class EntityEventHandler {

    /* We must subscribe to the event or we will never receive it..
    *   Plus: we must add the event handler to the event bus (see postInit() in StarterKits.java)
     */

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        //StarterKits.common.proxy.Proxy.printMessageToPlayer("Some chat text"); /* DOES NOT WORK YET */
        FMLLog.info("[StarterKits]: ".concat("onPLayerLogin Event Triggered"));

    }
}
