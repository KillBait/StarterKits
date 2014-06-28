package killbait.starterkits.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import killbait.starterkits.common.utils.LogHelper;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class EntityEventHandler {

    /* We must subscribe to the event or we will never receive it..
    *   Plus: we must add the event handler to the event bus (see postInit() in StarterKits.java)
     */

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        LogHelper.info("onPLayerLogin Event Triggered");

        // Check if the player joining has been give a starter kit

        if (!event.player.getEntityData().hasKey("StarterKitGiven"))
        {
            LogHelper.info("New Player joined.. Giving Starter Kit to " + event.player.getDisplayName());
            //event.player.getEntityData().setBoolean("GivenKit", true);
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Welcome " + EnumChatFormatting.RED + event.player.getDisplayName() + EnumChatFormatting.YELLOW + "......." + EnumChatFormatting.RESET));
            event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "have this chest of goodies to get you started :)" + EnumChatFormatting.RESET));

        }

        /*NBTTagCompound compound = new NBTTagCompound();
        event.player.writeToNBT(compound);
        if (compound.hasKey("GivenKit"))
            {
                LogHelper.info("GivenKit Detected");
            }
            else
            {
                if (event.player.getEntityData().hasKey("GivenKit"))
                {
                    LogHelper.info("Success");
                }
                LogHelper.info("No GivenKit Detected");
                //compound.setBoolean("GivenKit", true);
                event.player.getEntityData().setBoolean("GivenKit", true);

            }
        checkGivenStarterkit();*/

    }

    public void checkGivenStarterkit() {

    }
}
