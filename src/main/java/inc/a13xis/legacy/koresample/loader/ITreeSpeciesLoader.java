package inc.a13xis.legacy.koresample.loader;

import inc.a13xis.legacy.koresample.common.util.slab.TheSingleSlabRegistry;
import inc.a13xis.legacy.koresample.tree.TreeBlockFactory;

public interface ITreeSpeciesLoader {
	// Its dangerous to code alone! Use THIS: TreeTaxonomy taxonomy;
	TheSingleSlabRegistry slabRegistry = TheSingleSlabRegistry.REFERENCE;

	void loadLeavesBlocks(TreeBlockFactory factory);

	void loadLogBlocks(TreeBlockFactory factory);

	void loadSaplingBlocks(TreeBlockFactory factory);

	void loadSlabBlocks(TreeBlockFactory factory);

	void loadStairsBlocks(TreeBlockFactory factory);

	void loadWoodBlocks(TreeBlockFactory factory);

	void loadDoorBlocks(TreeBlockFactory factory);
}
