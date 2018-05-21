package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import net.minecraft.block.Block;

public interface DefinesStairs {
	void assignStairsBlock(StairsBlock block);

	StairsBlock stairsBlock();

	Block stairsModelBlock();

	Enum stairsModelSubBlockVariant();

	String stairsName();
}
