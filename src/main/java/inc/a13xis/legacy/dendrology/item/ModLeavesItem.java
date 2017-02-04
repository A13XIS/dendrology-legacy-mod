package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.item.LeavesItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ModLeavesItem extends LeavesItem {
    Block leaves;
    public ModLeavesItem(Block block)
    {
        super(block);
        this.leaves=block;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + ((LeavesBlock)leaves).getWoodType(stack.getMetadata()).name().toLowerCase();
    }
}
