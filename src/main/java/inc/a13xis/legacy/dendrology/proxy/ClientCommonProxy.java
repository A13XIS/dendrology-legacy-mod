package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.content.ParcelManager;
import inc.a13xis.legacy.dendrology.item.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ClientCommonProxy extends CommonProxy {

    @Override
    public void registerRenders(){
        //ModItems.registerAllItemRenders();
        ModBlocks.registerAllBlockRenders();
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote)
        {
            final ItemStack content = ParcelManager.INSTANCE.randomItem();

            final String message;
            if (content == null)
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.empty"):I18n.format(TheMod.MOD_ID+":parcel.empty");
            else
            {
                final String itemName = I18n.format(content.getItem().getUnlocalizedName(content) + ".name");
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.full",itemName):I18n.format(TheMod.MOD_ID+":parcel.full");

                final EntityItem entityItem = player.dropItem(content,false);
                if (entityItem != null)
                {
                    entityItem.setPickupDelay(0);
                    entityItem.setOwner(player.getCommandSenderEntity().getName());
                }
            }

            player.addChatMessage(new TextComponentString(message));
            itemStack.stackSize--;
        }
        return itemStack;
    }

}
