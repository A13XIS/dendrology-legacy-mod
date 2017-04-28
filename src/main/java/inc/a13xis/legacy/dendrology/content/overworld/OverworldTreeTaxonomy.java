package inc.a13xis.legacy.dendrology.content.overworld;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.koresample.tree.*;

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

    @Override
    public Iterable<? extends DefinesDoor> doorDefinitions() {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }

    @Override
    public Iterable<? extends DefinesFence> fenceDefinitions() {
        return ImmutableList.copyOf(OverworldTreeSpecies.values());
    }
}
