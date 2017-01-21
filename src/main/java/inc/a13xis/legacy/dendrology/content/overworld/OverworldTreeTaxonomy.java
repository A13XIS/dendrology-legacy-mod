package inc.a13xis.legacy.dendrology.content.overworld;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import inc.a13xis.legacy.koresample.tree.DefinesWood;
import inc.a13xis.legacy.koresample.tree.TreeTaxonomy;

public final class OverworldTreeTaxonomy implements TreeTaxonomy
{
    @Override
    public Iterable<? extends DefinesLeaves> leavesDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesLog> logDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesSapling> saplingDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesSlab> slabDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesWood> woodDefinitions()
    {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }
}
