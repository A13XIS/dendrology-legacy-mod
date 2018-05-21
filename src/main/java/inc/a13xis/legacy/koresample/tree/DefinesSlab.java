package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import net.minecraft.block.Block;

public interface DefinesSlab {

	void assignDoubleSlabBlock(SlabBlock doubleSlabBlock);

	void assignSingleSlabBlock(SlabBlock block);

	void assignSlabSubBlockVariant(Enum index);

	SlabBlock doubleSlabBlock();

	SlabBlock singleSlabBlock();

	Enum slabSubBlockVariant();

	Block slabModelBlock();

	Enum slabModelSubBlockVariant();

	String slabName();
}
