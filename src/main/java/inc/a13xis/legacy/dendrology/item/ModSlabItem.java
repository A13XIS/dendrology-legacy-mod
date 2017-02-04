package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.ModSlab2Block;
import inc.a13xis.legacy.dendrology.block.ModSlabBlock;
import inc.a13xis.legacy.koresample.tree.item.SlabItem;
import net.minecraft.block.Block;

public final class ModSlabItem extends SlabItem
{
    public ModSlabItem(Block block, ModSlabBlock singleSlab, ModSlabBlock doubleSlab)
    {
        super(block, singleSlab, doubleSlab);
    }

    public ModSlabItem(Block block, ModSlab2Block singleSlab, ModSlab2Block doubleSlab)
    {
        super(block, singleSlab, doubleSlab);
    }
}
