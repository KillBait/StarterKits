package killbait.starterkits.common.inventory;

import killbait.starterkits.common.item.ItemKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class InventoryKitCreator implements IInventory {

    private String name = "Kit Creator";

    // Define our inventory size
    public static final int INV_SIZE = 27;

    // The inventory's size must be same as number of slots you add to the Container class
    private ItemStack[] inventory = new ItemStack[INV_SIZE];

    // Provide a NBT Tag Compound to reference
    private final ItemStack invStack;

    // * @param itemstack - the ItemStack to which this inventory belongs
    public InventoryKitCreator(ItemStack stack)
    {
        this.invStack = stack;
        // Just in case the itemstack doesn't yet have an NBT Tag Compound:
        if (!invStack.hasTagCompound())
        {
            invStack.setTagCompound(new NBTTagCompound());
        }
         // note that it's okay to use stack instead of invItem right there
         // both reference the same memory location, so whatever you change using
         // either reference will change in the other

        // Read the inventory contents from NBT
        readFromNBT(invStack.getTagCompound());
        LogHelper.info("Inventory" + invStack.getTagCompound());
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = getStackInSlot(slot);
        if(stack != null)
        {
            if(stack.stackSize > amount)
            {
                stack = stack.splitStack(amount);
                markDirty();
            }
            else
            {
                setInventorySlotContents(slot, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        if(stack != null)
        {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return name;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return name.length() > 0;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    // 1.7.2 change to markDirty
    @Override
    public void markDirty()
    {
        for (int i = 0; i < getSizeInventory(); ++i)
        {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
                inventory[i] = null;
        }
        // be sure to write to NBT when the inventory changes!
        writeToNBT(invStack.getTagCompound());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
    // this will close the inventory if the player tries to move
    // the item that opened it, but you need to return this method
    // from the Container's canInteractWith method
    // an alternative would be to override the slotClick method and
    // prevent the current item slot from being clicked
        return player.getHeldItem() == invStack;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    /**
     * This method doesn't seem to do what it claims to do, as
     * items can still be left-clicked and placed in the inventory
     * even when this returns false
     */

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        // Don't want to be able to store the inventory item within itself
        // Bad things will happen, like losing your inventory
        // Actually, this needs a custom Slot to work
        // TODO: Double check this works
        return !(stack.getItem() instanceof ItemKitCreator);
    }

    /**
     * A custom method to read our inventory from an ItemStack's NBT compound
     */
    public void readFromNBT(NBTTagCompound compound) {
        // now you must include the NBTBase type ID when getting the list; NBTTagCompound's ID is 10
        // TODO: Check if this is correct
        NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); ++i) {
            // tagAt(int) has changed to getCompoundTagAt(int)
            NBTTagCompound item = items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    /**
     * A custom method to write our inventory to an ItemStack's NBT compound
     */
    public void writeToNBT(NBTTagCompound compound)
    {
        // Create a new NBT Tag List to store itemstacks as NBT Tags
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i)
        {
            // Only write stacks that contain items
            if (getStackInSlot(i) != null)
            {
                // Make a new NBT Tag Compound to write the itemstack and slot index to
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", (byte) i);
                // Writes the itemstack in slot(i) to the Tag Compound we just made
                getStackInSlot(i).writeToNBT(item);

                // add the tag compound to our tag list
                items.appendTag(item);
            }
        }
        // Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
        compound.setTag("ItemInventory", items);
    }


}
