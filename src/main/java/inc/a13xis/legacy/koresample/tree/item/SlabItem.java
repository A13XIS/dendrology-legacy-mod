package inc.a13xis.legacy.koresample.tree.item;

import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;

public abstract class SlabItem extends ItemSlab {
	// This provides a reminder that you must extend this class and change the constructor to accept your extension of
	// SlabBlock in the second and third  parameters
	protected SlabItem(Block block, SlabBlock singleSlab, SlabBlock doubleSlab) {
		super(block, singleSlab, doubleSlab);
	}
}
