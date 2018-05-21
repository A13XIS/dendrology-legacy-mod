package inc.a13xis.legacy.koresample.tree;

public interface TreeTaxonomy {
	Iterable<? extends DefinesLeaves> leavesDefinitions();

	Iterable<? extends DefinesLog> logDefinitions();

	Iterable<? extends DefinesSapling> saplingDefinitions();

	Iterable<? extends DefinesSlab> slabDefinitions();

	Iterable<? extends DefinesStairs> stairsDefinitions();

	Iterable<? extends DefinesDoor> doorDefinitions();

	Iterable<? extends DefinesWood> woodDefinitions();
}
