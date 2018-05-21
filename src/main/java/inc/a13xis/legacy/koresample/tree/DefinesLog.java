package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import net.minecraft.block.Block;

public interface DefinesLog {
	void assignLogBlock(LogBlock block);

	void assignLogSubBlockVariant(Enum index);

	Block logBlock();

	Enum logSubBlockVariant();

	String speciesName();

	Block woodBlock();

	Enum woodSubBlockVariant();
}
