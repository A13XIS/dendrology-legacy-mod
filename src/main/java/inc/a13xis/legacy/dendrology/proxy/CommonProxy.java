package inc.a13xis.legacy.dendrology.proxy;


import inc.a13xis.legacy.dendrology.content.ParcelManager;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommonProxy {
    public void registerRenders(){

    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        EntityItem entityItem = player.dropItem(ParcelManager.INSTANCE.randomItem(),false);
        if (entityItem != null)
        {
            entityItem.setPickupDelay(0);
            entityItem.setOwner(player.getCommandSenderEntity().getName());
        }
        return itemStack;
    }

    public void registerColorMultiplier(Iterable<? extends DefinesLeaves> subblocks){

    }

}
