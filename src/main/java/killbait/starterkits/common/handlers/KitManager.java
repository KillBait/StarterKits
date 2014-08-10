package killbait.starterkits.common.handlers;

import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.DimensionManager;

import java.io.*;

public class KitManager {

    public static void ProcessNBT(int kit) {


        NBTTagCompound comp = new NBTTagCompound();



        NBTTagList SlotList = new NBTTagList();
        ItemStack item;

        for (int j = 0; j < Reference.serverContainer.getInvSize(); ++j) {

            LogHelper.info("Processing Kit " + kit + " Slot" + j + " : " + Reference.serverContainer.getSlotContents(j));
            NBTTagCompound newtag = new NBTTagCompound();
            item = Reference.serverContainer.getSlotContents(j);
            newtag.setShort("Slot",(short) j);
            if (item != null) {
                item.writeToNBT(newtag);
                newtag.setInteger("Damage" , item.getItemDamage());
                //newtag.setInteger("Quantity", item.stackSize );
            }

            SlotList.appendTag(newtag);
        }

        comp.setTag("Items", SlotList);

        saveNBT(kit,comp);
    }



    public static void saveNBT(int kitnumber, NBTTagCompound nbt) {
        String fileName = "kitdata_"+kitnumber+".dat";
        File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
        //FileInputStream fis = null; //this makes it per-world

        //NBTTagCompound comp = new NBTTagCompound();
        //comp = (NBTTagCompound) nbt.copy();

        /*try {
            fis = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataInputStream instream = new DataInputStream(fis);
        try {
            nbt = CompressedStreamTools.read(instream);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            CompressedStreamTools.write(nbt, myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            instream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

}
