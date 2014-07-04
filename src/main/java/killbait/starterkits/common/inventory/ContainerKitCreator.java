package killbait.starterkits.common.inventory;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import killbait.starterkits.common.item.ItemKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.NBTHelper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ContainerKitCreator extends Container {

    public static final int KITCREATOR_INVENTORY_ROWS = 3;
    public static final int KITCREATOR_INVRNTORY_COLUMNS = 9;

    private final EntityPlayer entityPlayer;
    private final InventoryKitCreator inventoryKitCreator;

    // Player Inventory
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private int kitCreatorInventoryRows;
    private int kitCreatorInventoryColumns;

    public ContainerKitCreator(EntityPlayer entityPlayer, InventoryKitCreator inventoryKitCreator) {

        this.entityPlayer = entityPlayer;
        this.inventoryKitCreator = inventoryKitCreator;

        kitCreatorInventoryRows = KITCREATOR_INVENTORY_ROWS;
        kitCreatorInventoryColumns = KITCREATOR_INVRNTORY_COLUMNS;

        // Add the KitCreator slots to the container
        for (int bagRowIndex = 0; bagRowIndex < kitCreatorInventoryRows; ++bagRowIndex)
        {
            for (int bagColumnIndex = 0; bagColumnIndex < kitCreatorInventoryColumns; ++bagColumnIndex)
            {
                this.addSlotToContainer(new SlotKitCreator(this, inventoryKitCreator, entityPlayer, bagColumnIndex + bagRowIndex * kitCreatorInventoryColumns, 17 + bagColumnIndex * 18, 49 + bagRowIndex * 18));
            }
        }

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 17 + inventoryColumnIndex * 18, 126 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, actionBarSlotIndex, 17 + actionBarSlotIndex * 18, 184));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.worldObj.isRemote)
        {
            InventoryPlayer invPlayer = entityPlayer.inventory;
            for (ItemStack itemStack : invPlayer.mainInventory)
            {
                if (itemStack != null)
                {
                    if (NBTHelper.hasTag(itemStack, "kitCreatorGuiOpen"))
                                                {
                        NBTHelper.removeTag(itemStack, "kitCreatorGuiOpen");
                    }
                }
            }

            saveInventory(entityPlayer);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            // Attempt to shift click something from the bag inventory into the player inventory
            if (slotIndex < kitCreatorInventoryRows * kitCreatorInventoryColumns)
            {
                if (!this.mergeItemStack(itemStack, kitCreatorInventoryRows * kitCreatorInventoryColumns, inventorySlots.size(), false))
                {
                    return null;
                }
            }
            // Special case if we are dealing with an Alchemical Bag being shift clicked
            else if (itemStack.getItem() instanceof ItemKitCreator)
            {
                // Attempt to shift click a bag from the player inventory into the hot bar inventory
                if (slotIndex < (kitCreatorInventoryRows * kitCreatorInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS))
                {
                    if (!this.mergeItemStack(itemStack, (kitCreatorInventoryRows * kitCreatorInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), inventorySlots.size(), false))
                    {
                        return null;
                    }
                }
                // Attempt to shift click a bag from the hot bar inventory into the player inventory
                else if (!this.mergeItemStack(itemStack, kitCreatorInventoryRows * kitCreatorInventoryColumns, (kitCreatorInventoryRows * kitCreatorInventoryColumns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), false))
                {
                    return null;
                }
            }
            // Attempt to shift click a non-Alchemical Bag into the bag inventory
            else if (!this.mergeItemStack(itemStack, 0, kitCreatorInventoryRows * kitCreatorInventoryColumns, false))
            {
                return null;
            }


            if (itemStack.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            // We are on the server side.
            EntityPlayerMP player = (EntityPlayerMP) entityPlayer;
            LogHelper.info("Player Side");
        } else if (side == Side.CLIENT) {
            EntityClientPlayerMP player = (EntityClientPlayerMP) entityPlayer;
            LogHelper.info("Client Side");
            // We are on the client side.
        } else {
            // We have an errornous state!
        }
        return newItemStack;
    }

    public void saveInventory(EntityPlayer entityPlayer)
    {
        inventoryKitCreator.onGuiSaved(entityPlayer);
    }

    // TODO: Fix me!!! .. can still move the held KitCreator if you use the inventory shortcut keys

    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
        // this will prevent the player from interacting with the item that opened the inventory:
        //LogHelper.info(slot + " " + getSlot(slot) + " " + getSlot(slot).getStack() + " " + player.getHeldItem());
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        //LogHelper.info("changes");
    }

    public void dec()
    {
        this.inventoryKitCreator.decrStackSize(1, 1);
    }

    public void listItems()
    {
        if (this.inventoryKitCreator == null)
        {
            return;
        }
        else
        {
            int i = 0;
            float f = 0.0F;

            for (int j = 0; j < this.inventoryKitCreator.getSizeInventory(); ++j)
            {
                ItemStack itemstack = this.inventoryKitCreator.getStackInSlot(j);

                if (itemstack != null)
                {
                    LogHelper.info(" Container stack = " + itemstack + ", size =" + itemstack.stackSize);
                    //f += (float)itemstack.stackSize / (float)Math.min(par0IInventory.getInventoryStackLimit(), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            //f /= (float)par0IInventory.getSizeInventory();
            return;
        }
    }

}
