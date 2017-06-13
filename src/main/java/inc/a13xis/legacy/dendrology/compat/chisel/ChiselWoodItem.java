package inc.a13xis.legacy.dendrology.compat.chisel;

import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ChiselWoodItem extends ItemMultiTexture {
    public ChiselWoodItem(Block block,String[] subNames) {
        super(block,(ChiselWoodBlock)block,subNames);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return "chisel."+TheMod.getResourcePrefix()+stack.getItem().getRegistryName().getResourcePath().replace("_",".")+"."+ChiselWoodBlock.VARIATIONS.values()[stack.getItemDamage()].getName();
    }

}
