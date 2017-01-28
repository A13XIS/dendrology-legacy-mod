package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.ModLog2Block;
import inc.a13xis.legacy.dendrology.block.ModLog3Block;
import inc.a13xis.legacy.dendrology.block.ModLog4Block;
import inc.a13xis.legacy.dendrology.block.ModLogBlock;
import inc.a13xis.legacy.koresample.tree.item.LogItem;
import net.minecraft.block.Block;

public final class ModLogItem extends LogItem
{
    public ModLogItem(Block block, ModLogBlock log, String[] names) { super(block, log, names); }
    public ModLogItem(Block block, ModLog2Block log, String[] names) { super(block, log, names); }
    public ModLogItem(Block block, ModLog3Block log, String[] names) { super(block, log, names); }
    public ModLogItem(Block block, ModLog4Block log, String[] names) { super(block, log, names); }
}
