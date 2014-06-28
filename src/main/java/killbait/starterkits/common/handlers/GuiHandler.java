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
            LogHelper.info("111111111111");
            return new ContainerKitCreator(player,  new InventoryKitCreator(player.getHeldItem()));
            //return new ContainerAlchemicalBag(player, new InventoryAlchemicalBag(player.getHeldItem()));
        } else {
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
        if (guiId == Reference.GUI_INDEX_KITCREATOR)  {
            LogHelper.info("22222222222222222");
            return new GuiKitCreator(player, new InventoryKitCreator(player.getHeldItem()));
            //return new GuiKitCreator(player, new InventoryKitCreator(player.getHeldItem()));
        } else {
            return null;
        }
    }
}
