package killbait.starterkits.common.handlers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import killbait.starterkits.StarterKits;
import killbait.starterkits.common.utils.LogHelper;
import killbait.starterkits.common.utils.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ConfigHandler {

   public static void init(File configFile) {

       //Testing
       StarterKits.proxy.initClientConfiguration(configFile);



        LogHelper.info("Config loaded: ");
        Configuration config = new Configuration(configFile);

        config.load();

        //Notice there is nothing that gets the value of this property so the expression results in a Property object.
        Property someProperty = config.get("Permissions", "AllowedToUseCreator", "");

        // Here we add a comment to our new property.
        someProperty.comment = "Names of people allowed to use the KitCreator, separate names with the ; symbol e.g. S:AllowedToUseItem=SomeOwnerPlayerName;SomeAdminPLayerName";

       if ( someProperty.getString().length() != 0)
       {
           //LogHelper.info("allowed = " + someProperty.getString() + " !!");
           Reference.permissionList = Arrays.asList(Pattern.compile(";").split(someProperty.getString()));
           /*for (String s: Reference.permissionList)
           {
               System.out.println(s);
           }*/
       }
       else
        {
            LogHelper.warn("No Usernames detected in the config allowed list. KitCreator will be unusable by anyone until set properly");
        }



        //config.getString(Names.tutBlock_name, Ids.tutBlock_default).getInt();

        config.save();
    }
}