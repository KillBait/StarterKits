package killbait.starterkits.common.inventory;

import killbait.starterkits.common.item.ItemKitCreator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotKitCreator extends Slot {

    private final EntityPlayer entityPlayer;
    private ContainerKitCreator containerKitCreator;

    public SlotKitCreator(ContainerKitCreator containerKitCreator, IInventory inventory, EntityPlayer entityPlayer, int x, int y, int z)
    {
        super(inventory, x, y, z);
        this.entityPlayer = entityPlayer;
        this.containerKitCreator = containerKitCreator;
    }

    @Override
    public void onSlotChange(ItemStack itemStack1, ItemStack itemStack2)
    {
        super.onSlotChange(itemStack1, itemStack2);
        /*containerKitCreator.saveInventory(entityPlayer);*/
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemKitCreator ? false : true;
    }


}
