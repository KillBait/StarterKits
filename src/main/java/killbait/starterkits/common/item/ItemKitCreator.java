package killbait.starterkits.common.item;

import killbait.starterkits.StarterKits;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentTranslation;
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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                LogHelper.info("GUI");
                // Set a UUID on the Alchemical Bag, if one doesn't exist already
                /*NBTHelper.setUUID(itemStack);
                NBTHelper.setBoolean(itemStack, Names.NBT.ALCHEMICAL_BAG_GUI_OPEN, true);*/
                player.openGui(StarterKits.instance, Reference.GUI_INDEX_KITCREATOR, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            } else {
                LogHelper.info("Diamonds");
                new InventoryKitCreator(player.getHeldItem()).setInventorySlotContents(0, new ItemStack(Items.diamond, 4));
            }
        }
        return stack;
    }
}