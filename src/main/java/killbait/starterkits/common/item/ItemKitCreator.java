package killbait.starterkits.common.item;

import killbait.starterkits.StarterKits;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.NBTHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemKitCreator extends Item {
    public ItemKitCreator() {
        maxStackSize = 1;
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("kitCreator");
        setTextureName("StarterKits:KitCreator");
    }

    /*@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ) {

        // check if we triggering client side, if we are, abort
        //if (world.isRemote) return false;

        //Get the coords of the clicked block, if it's not a TE it will be null
        TileEntity te = world.getTileEntity(X, Y, Z);

        // make sure we clicked on a Tile Entity and it is a chest
        if (te != null && te instanceof TileEntityChest) {
            player.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + " " + world.isRemote + " " + EnumChatFormatting.RESET + " White"));
        }

        return true;

    }*/

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if ( Reference.permissionList.contains(player.getDisplayName())) {
            if (!world.isRemote) {
                //LogHelper.info("GUI");
                NBTHelper.setUUID(itemStack);
                player.openGui(StarterKits.instance, Reference.GUI_INDEX_KITCREATOR, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }
        else
        {
            if (!world.isRemote) {
                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You do not have permission to use this " + EnumChatFormatting.RESET));
            }
            LogHelper.warn("Use permission denied for " + player.getDisplayName());

        }

        return itemStack;
    }
}