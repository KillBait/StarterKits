package killbait.starterkits.common.proxy;

import cpw.mods.fml.common.FMLLog;

public class Proxy {

    public void printMessageToPlayer(String msg) {
    }

    public void LogMessage(String msg){

        FMLLog.info("[StarterKits]: ".concat(msg));
    }

}
