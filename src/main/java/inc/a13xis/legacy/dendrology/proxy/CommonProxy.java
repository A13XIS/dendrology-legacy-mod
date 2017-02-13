package inc.a13xis.legacy.dendrology.proxy;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommonProxy {
    public void registerRenders(){

    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        return itemStack;
    }

}
