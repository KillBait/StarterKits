package killbait.starterkits.common.utils;

import killbait.starterkits.common.inventory.ContainerKitCreator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Reference {

    // Mod related stuff

    public static final String MOD_ID = "StarterKits";
    public static final String MOD_NAME = "StarterKits";
    public static final String MOD_VERSION = "1.7.2-0.1";

    // Booleans

    public static final boolean DEBUG = true;

    // Strings

    public static List<String> permissionList = new ArrayList<String>();



    // GUI related stuff

    public static final int GUI_INDEX_KITCREATOR = 0;
    public static ContainerKitCreator serverContainer;


    // Names of people allowed to use the KitCreator

    public static Array String[];

    // network packet ID

    public static final int GUIBUTTON_PACKET_ID = 0;
}
