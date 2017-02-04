package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.ModLog2Block;
import inc.a13xis.legacy.dendrology.block.ModLog3Block;
import inc.a13xis.legacy.dendrology.block.ModLog4Block;
import inc.a13xis.legacy.dendrology.block.ModLogBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.item.LogItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ModLogItem extends LogItem
{
    LogBlock logblock;
    public ModLogItem(Block block, ModLogBlock log, String[] names) { super(block, log, names); this.logblock = log;}
    public ModLogItem(Block block, ModLog2Block log, String[] names) { super(block, log, names); this.logblock = log;}
    public ModLogItem(Block block, ModLog3Block log, String[] names) { super(block, log, names); this.logblock = log;}
    public ModLogItem(Block block, ModLog4Block log, String[] names) { super(block, log, names); this.logblock = log;}

    @Override
    public String getUnlocalizedName(ItemStack stack){
        Enum variant = logblock instanceof ModLogBlock? (Enum)logblock.getStateFromMeta(stack.getItemDamage()).getValue(ModLogBlock.VARIANT)
                      :logblock instanceof ModLog2Block? (Enum)logblock.getStateFromMeta(stack.getItemDamage()).getValue(ModLog2Block.VARIANT)
                      :logblock instanceof ModLog3Block? (Enum)logblock.getStateFromMeta(stack.getItemDamage()).getValue(ModLog3Block.VARIANT)
                      :logblock instanceof ModLog4Block? (Enum)logblock.getStateFromMeta(stack.getItemDamage()).getValue(ModLog4Block.VARIANT)
                      : null;
        return super.getUnlocalizedName()+"."+variant.name().toLowerCase();
    }
}
