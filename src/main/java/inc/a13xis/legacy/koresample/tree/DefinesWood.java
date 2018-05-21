package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.tree.block.WoodBlock;

public interface DefinesWood {
	void assignWoodBlock(WoodBlock block);

	void assignWoodSubBlockVariant(Enum index);

	WoodBlock woodBlock();

	Enum woodSubBlockVariant();

	String speciesName();
}
