package killbait.starterkits.common.inventory;

import killbait.starterkits.common.utils.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerKitCreator extends Container{

    /* Small Bag
    public static final int KITCREATOR_INVENTORY_ROWS = 3;
    public static final int KITCREATOR_INVRNTORY_COLUMNS = 9;

    private final EntityPlayer entityPlayer;
    private final InventoryKitCreator inventoryKitCreator;

    // Player Inventory
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private int bagInventoryRows;
    private int bagInventoryColumns;*/

    /** The Item Inventory for this Container */
    private final InventoryKitCreator inventory;

    /** Using these will make transferStackInSlot easier to understand and implement
     * INV_START is the index of the first slot in the Player's Inventory, so our
     * InventoryItem's number of slots (e.g. 5 slots is array indices 0-4, so start at 5)
     * Notice how we don't have to remember how many slots we made? We can just use
     * InventoryItem.INV_SIZE and if we ever change it, the Container updates automatically. */
    private static final int INV_START = InventoryKitCreator.INV_SIZE, INV_END = INV_START+26,
            HOTBAR_START = INV_END+1, HOTBAR_END = HOTBAR_START+8;

    // If you're planning to add armor slots, put those first like this:
    // ARMOR_START = InventoryItem.INV_SIZE, ARMOR_END = ARMOR_START+3,
    // INV_START = ARMOR_END+1, and then carry on like above.

    public ContainerKitCreator(EntityPlayer player, InventoryPlayer inv, InventoryKitCreator kit)
    {
        int i = 0;
        inventory = kit;

        /* CUSTOM INVENTORY SLOTS
        for (i = 0; i < InventoryKitCreator.INV_SIZE; ++i) {
            addSlotToContainer(new SlotKitCreator(inventory, i, 80 + (18*(i%5)), 8 + (18 * (int)(i/5))));
        }*/

        // PLAYER INVENTORY - uses default locations for standard inventory texture file
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
            }
        }

        // PLAYER ACTION BAR - uses default locations for standard action bar texture file
        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(inv, i, 8 + i * 18, 165));
        }
        LogHelper.info("container");
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
// be sure to return the inventory's isUseableByPlayer method
// if you defined special behavior there:
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     * Only real change we make to this is to set needsUpdate to true at the end
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // If item is in our custom Inventory
            if (index < INV_START)
            {
                // try to place in player inventory / action bar
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            // Item is in inventory / hotbar, try to place in custom inventory or armor slots
            /*else
            {
				if (index >= INV_START)
				{
					// place in custom inventory
					if (!this.mergeItemStack(itemstack1, 0, ARMOR_START, false))
					{
						return null;
					}
				}
			}*/

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    // NOTE: The following is necessary if you want to prevent the player from moving the item while the
// inventory is open; if you don't and the player moves the item, the inventory will not be able to save properly
    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
// this will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }

    /*@Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards)
    {
        boolean flag1 = false;
        int k = (backwards ? end - 1 : start);
        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable())
        {
            while (stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start))
            {
                slot = (Slot) inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (!slot.isItemValid(stack)) {
                    continue;
                }

                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() &&
                        (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) &&
                        ItemStack.areItemStackTagsEqual(stack, itemstack1))
                {
                    int l = itemstack1.stackSize + stack.stackSize;

                    if (l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        inventory.markDirty();
                        flag1 = true;
                    } else if (itemstack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit()) {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        inventory.markDirty();
                        flag1 = true;
                    }
                }

                k += (backwards ? -1 : 1);
            }
        }

        if (stack.stackSize > 0)
        {
            k = (backwards ? end - 1 : start);

            while (!backwards && k < end || backwards && k >= start) {
                slot = (Slot) inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (!slot.isItemValid(stack)) {
                    continue;
                }

                if (itemstack1 == null) {
                    int l = stack.stackSize;

                    if (l <= slot.getSlotStackLimit()) {
                        slot.putStack(stack.copy());
                        stack.stackSize = 0;
                        inventory.markDirty();
                        flag1 = true;
                        break;
                    } else {
                        putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
                        stack.stackSize -= slot.getSlotStackLimit();
                        inventory.markDirty();
                        flag1 = true;
                    }
                }

                k += (backwards ? -1 : 1);
            }
        }

        return flag1;
    }*/

}
