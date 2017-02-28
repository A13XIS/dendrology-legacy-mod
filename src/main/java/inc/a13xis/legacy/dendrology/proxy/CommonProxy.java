package inc.a13xis.legacy.dendrology.proxy;


import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class CommonProxy {
    public void registerRenders(){}

    public void onItemRightClick(ItemStack content, World world, EntityPlayer player){
        final String message;
        if (content == null)
            message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeServerTranslate(null,TheMod.MOD_ID+":parcel.empty"): I18n.translateToFallback(TheMod.MOD_ID+":parcel.empty");
        else
        {
            final String itemName = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeServerTranslate(null,content.getItem().getUnlocalizedName(content) + ".name"):I18n.translateToFallback(content.getItem().getUnlocalizedName(content) + ".name");
            message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeServerTranslate(null,TheMod.MOD_ID+":parcel.full",itemName):I18n.translateToFallback(TheMod.MOD_ID+":parcel.full");

        }

        player.sendMessage(new TextComponentString(message));
    }

    public String safeTranslate(String settingName) {
        return TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeServerTranslate(null,settingName):I18n.translateToFallback(settingName);
    }
}
