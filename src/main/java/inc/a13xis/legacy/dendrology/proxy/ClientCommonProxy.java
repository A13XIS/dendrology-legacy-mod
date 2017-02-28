package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ClientCommonProxy extends CommonProxy {

    @Override
    public void registerRenders(){
        ModItems.registerAllItemRenders();
        ModBlocks.registerAllBlockRenders();
    }

    @Override
    public void onItemRightClick(ItemStack content, World world, EntityPlayer player) {


            final String message;
            if (content == null)
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.empty"): net.minecraft.client.resources.I18n.format(TheMod.MOD_ID+":parcel.empty");
            else
            {
                final String itemName = net.minecraft.client.resources.I18n.format(content.getItem().getUnlocalizedName(content) + ".name");
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.full",itemName):net.minecraft.client.resources.I18n.format(TheMod.MOD_ID+":parcel.full");

            }

            player.sendMessage(new TextComponentString(message));
    }

    @Override
    public String safeTranslate(String settingName) {
        return TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,settingName): net.minecraft.client.resources.I18n.format(settingName);
    }

}
