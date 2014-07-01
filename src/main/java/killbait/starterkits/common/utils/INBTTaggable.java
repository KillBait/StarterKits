package killbait.starterkits.common.utils;

import net.minecraft.nbt.NBTTagCompound;

/* This class was borrowed from Equivalent Exchange GitHub page
*
* All credits for this class goto Pahimar
*/

public interface INBTTaggable
{
    void readFromNBT(NBTTagCompound nbtTagCompound);

    void writeToNBT(NBTTagCompound nbtTagCompound);
}
