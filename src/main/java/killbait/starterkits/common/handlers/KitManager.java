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

    static File saveDir;

    public static void ProcessNBT(int kit) {

        // Create Main Tree
        NBTTagCompound main = new NBTTagCompound();

        // Create and set Items Branch
        NBTTagCompound list = new NBTTagCompound();
        main.setTag("Items",list);

        // Write each slots info to a new Tag in Items Branch

        //ItemStack item;
        String name;

        for (int j = 0; j < Reference.serverContainer.getInvSize(); ++j) {

            if (Reference.DEBUG) LogHelper.info("Processing Kit " + kit + " Slot" + j + " : " + Reference.serverContainer.getSlotContents(j));
            NBTTagCompound newtag = new NBTTagCompound();
            ItemStack item = Reference.serverContainer.getSlotContents(j);
            if (item != null) {
                item.writeToNBT(newtag);
                newtag.setInteger("Damage" , item.getItemDamage());
            }
            if (j <= 9) {
                name = "Slot0" + j;}
            else {
                name = "Slot" + j;

            }
            list.setTag(name,newtag);

        }

        // Save all the NBT to disk
        saveNBT(kit,main);
    }



    public static void saveNBT(int kitnumber, NBTTagCompound nbt) {

        saveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "StaterKits");

        try {

            if (!saveDir.exists()) {
                saveDir.mkdir();
                LogHelper.info("Making Directory");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }



        String fileName = "kitdata_"+kitnumber+".dat";
        File myFile = new File(saveDir, fileName);
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
            LogHelper.info("Kit " + kitnumber + " data saved to disk");
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

    public static void LoadNBT(int kitnumber) {

    }

}
