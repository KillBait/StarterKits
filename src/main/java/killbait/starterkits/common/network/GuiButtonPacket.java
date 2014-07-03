package killbait.starterkits.common.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.item.ItemKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class GuiButtonPacket implements IMessage, IMessageHandler<GuiButtonPacket, IMessage> {

    private int number;
    private InventoryKitCreator kit;

    public GuiButtonPacket() { }

    public GuiButtonPacket(int number) {
        this.number = number;
        LogHelper.info("Packet is: " + this.number);

    }

    public GuiButtonPacket(InventoryKitCreator ikc) {
        this.kit = ikc;
        LogHelper.info("Packet is: " + this.kit);

    }


    @Override
    public void fromBytes(ByteBuf buf) {

        number = buf.readByte();
        Reference.serverContainer.saveInventory(Minecraft.getMinecraft().thePlayer);
        LogHelper.info("fromBytes = " + number);

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeByte(number);
        LogHelper.info("toBytes = " + buf + number);
        //InventoryKitCreator.decrStackSize(int slotIndex, int decrementAmount);

    }

    @Override
    public IMessage onMessage(GuiButtonPacket message, MessageContext ctx) {
        System.out.println(String.format("[ %s ] : Received %s from %s", FMLCommonHandler.instance().getEffectiveSide(), message.number, ctx.getServerHandler().playerEntity.getDisplayName()));
        return null; // no response in this case
    }
}

