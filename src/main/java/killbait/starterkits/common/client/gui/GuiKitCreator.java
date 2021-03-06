package killbait.starterkits.common.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import killbait.starterkits.StarterKits;
import killbait.starterkits.common.inventory.ContainerKitCreator;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.network.PacketHandler;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiKitCreator extends GuiContainer {

    private final ItemStack parentItemStack;
    private final InventoryKitCreator inventoryKitCreator;
    protected static final ResourceLocation bgLocation = new ResourceLocation(Reference.MOD_ID.toLowerCase() + ":textures/gui/kitcreator.png");
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    public GuiKitCreator(EntityPlayer entityPlayer, InventoryKitCreator inventoryKitCreator)
    {
        super(new ContainerKitCreator(entityPlayer, inventoryKitCreator));

        this.parentItemStack = inventoryKitCreator.parentItemStack;
        this.inventoryKitCreator = inventoryKitCreator;

        xSize = 194;
        ySize = 213;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        //make buttons
        //id, x, y, width, height, text
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButtonImage(1, i + 110, j + 24,14, 14,"",208,0,14,14,bgLocation));
        //this.buttonList.add(new GuiButtonImage(2, i + 90, j + 24,14, 14, "C",100,100,14,14,bgLocation));
        this.buttonList.add(new GuiButtonImage(3, i + 70, j + 24,14, 14, "",194,0,14,14,bgLocation));
        this.buttonList.add(new GuiButton(4, i + 145, j + 21,30,20,"Set"));
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRendererObj.drawString(StatCollector.translateToLocal("Select Kit Number"), 57, 10, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("Place Items, then click Set"), 15, ySize - 110 + 2, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("" + Reference.selectedKit), 94, 28, 4210752);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(bgLocation);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);

        //LogHelper.info(button.id);

        switch (button.id) {
            case 0:
                LogHelper.info("Button 0 Selected");
                break;
            case 1:
                //LogHelper.info("Send to server: Button 1 Selected");
                //StarterKits.networkWrapper.sendToServer(new GuiButtonPacket("Foobar"));
                StarterKits.networkWrapper.sendToServer(new PacketHandler(button.id));
                //StarterKits.networkWrapper.sendToServer(new GuiButtonPacket(this.inventoryKitCreator));
                //this.inventoryKitCreator.decrStackSize(1, 1);
                /*LogHelper.info("Inventory Size = " + this.inventoryKitCreator.getSizeInventory());
                if (this.inventoryKitCreator != null)
                {
                    int i = 0;
                    float f = 0.0F;

                    for (int j = 0; j < this.inventoryKitCreator.getSizeInventory(); ++j)
                    {
                        ItemStack itemstack = this.inventoryKitCreator.getStackInSlot(j);

                        if (itemstack != null)
                        {
                            LogHelper.info(" GUI stack before = " + itemstack + ", size =" + itemstack.stackSize);
                            //f += (float)itemstack.stackSize / (float)Math.min(par0IInventory.getInventoryStackLimit(), itemstack.getMaxStackSize());
                            ++i;
                        }
                    }
                }*/




                break;
            case 2:
                LogHelper.info("Button 2 Selected");
                break;
            case 3:
                LogHelper.info("Button 3 Selected");
                StarterKits.networkWrapper.sendToServer(new PacketHandler(button.id));
                break;
            case 4:
                LogHelper.info("Button 4 Selected");
                StarterKits.networkWrapper.sendToServer(new PacketHandler(button.id));
                break;
            default:
                LogHelper.info("default");

        }

    }
}
