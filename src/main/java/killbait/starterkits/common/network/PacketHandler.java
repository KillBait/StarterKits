package killbait.starterkits.common.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import killbait.starterkits.common.handlers.KitManager;
import killbait.starterkits.common.inventory.InventoryKitCreator;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.client.Minecraft;

import static killbait.starterkits.common.handlers.KitManager.*;

public class PacketHandler implements IMessage {

    private int number;
    private InventoryKitCreator kit;

    public PacketHandler() { }

    public PacketHandler(int number) {
        this.number = number;
        //LogHelper.info("Packet is: " + this.number);

    }

    public PacketHandler(InventoryKitCreator ikc) {
        this.kit = ikc;
        //LogHelper.info("Packet is: " + this.kit);


    }

    /* --------------------------------------------
    *
    *
    * NOTE: toBytes is executed first, then fromBytes()
    *
    *
    * -------------------------------------------- */



    // fromBytes() is always executed server side only

    @Override
    public void fromBytes(ByteBuf buf) {

        number = buf.readByte();
        //Reference.serverContainer.saveInventory(Minecraft.getMinecraft().thePlayer);
        //LogHelper.info("fromBytes = " + number);

        //Reference.serverContainer.getInventory();
        if (number == 1) {
            //Reference.serverContainer.dec();
            //Reference.serverContainer.listItems();
            if (Reference.selectedKit  <= 9 ) {
                Reference.selectedKit++;
            }

        }

        if (number == 3) {
            if (Reference.selectedKit >= 2) {
                Reference.selectedKit--;
            }
        }

        if (number == 4) {
            KitManager.ProcessNBT(Reference.selectedKit);
            LogHelper.info("Set");
        }

    }

    // toBytes is always triggered on the client side only

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeByte(number);
        //LogHelper.info("toBytes = " + buf + ", contents = " + number);
        //InventoryKitCreator.decrStackSize(int slotIndex, int decrementAmount);

    }

    public static class Handler implements IMessageHandler<PacketHandler, IMessage> {

        @Override
        public IMessage onMessage(PacketHandler message, MessageContext ctx) {
            System.out.println(String.format("[ %s ] : Received %s from %s", FMLCommonHandler.instance().getEffectiveSide(), message.number, ctx.getServerHandler().playerEntity.getDisplayName()));
            return null; // no response in this case
        }
    }
}
