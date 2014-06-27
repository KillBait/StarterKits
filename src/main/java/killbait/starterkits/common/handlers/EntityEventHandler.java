package killbait.starterkits.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import killbait.starterkits.common.utils.LogHelper;

public class EntityEventHandler {

    /* We must subscribe to the event or we will never receive it..
    *   Plus: we must add the event handler to the event bus (see postInit() in StarterKits.java)
     */

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        //StarterKits.common.proxy.Proxy.printMessageToPlayer("Some chat text"); /* DOES NOT WORK YET */
        //StarterKits.proxy.printMessageToPlayer("onPLayerLogin Event Triggered");
        LogHelper.info("onPLayerLogin Event Triggered");

    }
}
