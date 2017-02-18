package inc.a13xis.legacy.dendrology.proxy;


import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class CommonProxy {
    public void registerRenders(){}

    public void onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){}


    public String safeTranslate(String settingName) {
        String val=I18n.translateToLocal(settingName);
        return val==settingName?I18n.translateToFallback(settingName):val;
    }
}
