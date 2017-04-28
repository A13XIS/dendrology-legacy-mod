package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.*;
import inc.a13xis.legacy.koresample.TheMod;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.item.LeavesItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModLeavesItem extends LeavesItem {
    Block leaves;
    public ModLeavesItem(Block block)
    {
        super(block);
        this.leaves=(LeavesBlock) block;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String name;
        if(leaves instanceof ModLeavesBlock)
            name = ((ModLeavesBlock)leaves).resourcePrefix() +"leaves."+ ModLogBlock.EnumType.fromId(this.getDamage(stack)).getName();
        else if(leaves instanceof ModLeaves2Block)
            name = ((ModLeaves2Block)leaves).resourcePrefix()+"leaves."+ ModLog2Block.EnumType.fromId(this.getDamage(stack)).getName();
        else if(leaves instanceof ModLeaves3Block)
            name = ((ModLeaves3Block)leaves).resourcePrefix()+"leaves."+ ModLog3Block.EnumType.fromId(this.getDamage(stack)).getName();
        else
            name = ((ModLeaves4Block)leaves).resourcePrefix()+"leaves.tuopa";
        return "tile."+name;
    }
}
