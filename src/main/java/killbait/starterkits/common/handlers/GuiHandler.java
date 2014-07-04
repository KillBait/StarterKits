package killbait.starterkits.common.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import killbait.starterkits.common.client.gui.GuiKitCreator;
import killbait.starterkits.common.inventory.ContainerKitCreator;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == Reference.GUI_INDEX_KITCREATOR)  {



            ContainerKitCreator container = new ContainerKitCreator(player,  new InventoryKitCreator(player.getHeldItem()));
            Reference.serverContainer = container;
            LogHelper.info("ServerGuiElement = " + container);

            return container;

        } else {
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == Reference.GUI_INDEX_KITCREATOR)  {

            GuiKitCreator gui = new GuiKitCreator(player, new InventoryKitCreator(player.getHeldItem()));
            LogHelper.info("ClentGuiElement= " + gui);

            return gui;

        } else {
            return null;
        }
    }
}
