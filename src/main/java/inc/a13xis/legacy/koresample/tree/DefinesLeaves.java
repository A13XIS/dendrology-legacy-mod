package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;

public interface DefinesLeaves extends ProvidesColor {
	void assignLeavesBlock(LeavesBlock block);

	void assignLeavesSubBlockVariant(Enum variant);

	LeavesBlock leavesBlock();

	Enum leavesSubBlockVariant();

	DefinesSapling saplingDefinition();

	String speciesName();
}
