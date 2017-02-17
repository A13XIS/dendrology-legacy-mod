package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.content.ParcelManager;
import inc.a13xis.legacy.dendrology.item.ModItems;
import inc.a13xis.legacy.dendrology.world.Colorizer;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.Iterator;

public class ClientCommonProxy extends CommonProxy {

    @Override
    public void registerRenders(){
        ModItems.registerAllItemRenders();
        ModBlocks.registerAllBlockRenders();
    }

}
