package killbait.starterkits.common.proxy;

import cpw.mods.fml.common.FMLLog;


/*
 * Created by Jon on 24/06/2014.
 */

/* TODO
 *  Double check this class is NOT triggered client side when a player joins a dedicated server ???????
 */

public class ProxyClient extends Proxy{

    @Override
    public void printMessageToPlayer(String msg) {

        //IChatComponent ico;


        //ico.appendText("wwww");
        FMLLog.info("[StarterKits]: ".concat("Proxy Client Message"));
    }



}
