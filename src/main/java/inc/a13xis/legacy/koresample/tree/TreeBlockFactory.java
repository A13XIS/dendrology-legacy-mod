package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.common.util.slab.SingleDoubleSlab;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;

public interface TreeBlockFactory {
	LeavesBlock createLeavesBlock(Iterable<DefinesLeaves> subBlocks);

	LogBlock createLogBlock(Iterable<DefinesLog> subBlocks);

	SaplingBlock createSaplingBlock(Iterable<DefinesSapling> subBlocks);

	SingleDoubleSlab createSlabBlocks(Iterable<DefinesSlab> subBlocks);

	StairsBlock createStairsBlock(DefinesStairs definition);

	DoorBlock createDoorBlock(DefinesDoor definition);

	WoodBlock createWoodBlock(Iterable<DefinesWood> subBlocks);

	void resetTypeBit();
}
