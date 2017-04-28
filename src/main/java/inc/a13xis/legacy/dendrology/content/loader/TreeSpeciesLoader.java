package inc.a13xis.legacy.dendrology.content.loader;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.block.*;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.util.slab.SingleDoubleSlab;
import inc.a13xis.legacy.koresample.common.util.slab.TheSingleSlabRegistry;
import inc.a13xis.legacy.koresample.loader.ITreeSpeciesLoader;
import inc.a13xis.legacy.koresample.tree.*;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;

import java.util.List;

public class TreeSpeciesLoader implements ITreeSpeciesLoader
{
    private final TreeTaxonomy taxonomy;
    private final TheSingleSlabRegistry slabRegistry = TheSingleSlabRegistry.REFERENCE;

    public TreeSpeciesLoader(TreeTaxonomy taxonomy)
    {
        this.taxonomy = taxonomy;
    }

    public void load(TreeBlockFactory factory)
    {
        loadLogBlocks(factory);
        loadLeavesBlocks(factory);
        loadWoodBlocks(factory);
        loadSaplingBlocks(factory);
        loadSlabBlocks(factory);
        loadStairsBlocks(factory);
        loadDoorBlocks(factory);
    }

    public void loadLeavesBlocks(TreeBlockFactory factory)
    {
        int typebit=0;
        final List<DefinesLeaves> subBlocks = Lists.newArrayListWithCapacity(LeavesBlock.CAPACITY);
        for (final DefinesLeaves definition : taxonomy.leavesDefinitions())
        {
            if(typebit==0)
                definition.assignLeavesSubBlockVariant(ModLogBlock.EnumType.fromId(subBlocks.size()));
            else if(typebit==1)
                definition.assignLeavesSubBlockVariant(ModLog2Block.EnumType.fromId(subBlocks.size()));
            else if(typebit==2)
                definition.assignLeavesSubBlockVariant(ModLog3Block.EnumType.fromId(subBlocks.size()));
            else if(typebit==3)
                definition.assignLeavesSubBlockVariant(ModLog4Block.EnumType.getTuopa());

            subBlocks.add(definition);
            if (subBlocks.size() == LeavesBlock.CAPACITY)
            {
                factory.createLeavesBlock(subBlocks);
                typebit++;
                subBlocks.clear();
            }
        }
        if (!subBlocks.isEmpty()) factory.createLeavesBlock(subBlocks);
        factory.resetTypeBit();
    }

    public void loadLogBlocks(TreeBlockFactory factory)
    {
        int typebit = 0;
        final List<DefinesLog> subBlocks = Lists.newArrayListWithCapacity(LogBlock.CAPACITY);
        for (final DefinesLog definition : taxonomy.logDefinitions())
        {
            if(typebit==0)
                definition.assignLogSubBlockVariant(ModLogBlock.EnumType.fromId(subBlocks.size()));
            else if(typebit==1)
                definition.assignLogSubBlockVariant(ModLog2Block.EnumType.fromId(subBlocks.size()));
            else if(typebit==2)
                definition.assignLogSubBlockVariant(ModLog3Block.EnumType.fromId(subBlocks.size()));
            else if(typebit==3)
                definition.assignLogSubBlockVariant(ModLog4Block.EnumType.getTuopa());

            subBlocks.add(definition);
            if (subBlocks.size() == LogBlock.CAPACITY)
            {
                factory.createLogBlock(subBlocks);
                subBlocks.clear();
                typebit++;
            }
        }
        if (!subBlocks.isEmpty()) factory.createLogBlock(subBlocks);
        factory.resetTypeBit();
    }

    public void loadSaplingBlocks(TreeBlockFactory factory)
    {
        int typebit = 0;
        final List<DefinesSapling> subBlocks = Lists.newArrayListWithCapacity(SaplingBlock.CAPACITY);
        for (final DefinesSapling definition : taxonomy.saplingDefinitions())
        {
            if(typebit==0)
                definition.assignSaplingSubBlockVariant(ModSlabBlock.EnumType.fromId(subBlocks.size()));
            else if(typebit==1)
                definition.assignSaplingSubBlockVariant(ModSlab2Block.EnumType.fromId(subBlocks.size()));

            subBlocks.add(definition);
            if (subBlocks.size() == SaplingBlock.CAPACITY)
            {
                factory.createSaplingBlock(subBlocks);
                subBlocks.clear();
                typebit++;
            }
        }
        if (!subBlocks.isEmpty()) factory.createSaplingBlock(subBlocks);
        factory.resetTypeBit();
    }

    public void loadSlabBlocks(TreeBlockFactory factory)
    {
        final List<DefinesSlab> subBlocks = Lists.newArrayListWithCapacity(SlabBlock.CAPACITY);
        int typebit = 0;
        for (final DefinesSlab definition : taxonomy.slabDefinitions())
        {
            if(typebit==0)
                definition.assignSlabSubBlockVariant(ModSlabBlock.EnumType.fromId(subBlocks.size()));
            else if(typebit==1)
                definition.assignSlabSubBlockVariant(ModSlab2Block.EnumType.fromId(subBlocks.size()));

            subBlocks.add(definition);
            if (subBlocks.size() == SlabBlock.CAPACITY)
            {
                final SingleDoubleSlab slabs = factory.createSlabBlocks(subBlocks);
                slabRegistry.add(slabs.singleSlab());
                subBlocks.clear();
                typebit++;
            }
        }
        if (!subBlocks.isEmpty())
        {
            final SingleDoubleSlab slabs = factory.createSlabBlocks(subBlocks);
            slabRegistry.add(slabs.singleSlab());
        }
        factory.resetTypeBit();
    }

    public void loadStairsBlocks(TreeBlockFactory factory)
    {
        for (final DefinesStairs definition : taxonomy.stairsDefinitions())
        {
            factory.createStairsBlock(definition);
        }
    }

    public void loadDoorBlocks(TreeBlockFactory factory)
    {
        for (final DefinesDoor definition : taxonomy.doorDefinitions())
        {
            factory.createDoorBlock(definition);
        }
    }

    public void loadWoodBlocks(TreeBlockFactory factory)
    {
        final List<DefinesWood> subBlocks = Lists.newArrayListWithCapacity(WoodBlock.CAPACITY);
        for (final DefinesWood definition : taxonomy.woodDefinitions())
        {
                definition.assignWoodSubBlockVariant(ModWoodBlock.EnumType.fromId(subBlocks.size()));

            subBlocks.add(definition);
            if (subBlocks.size() == WoodBlock.CAPACITY)
            {
                factory.createWoodBlock(subBlocks);
                subBlocks.clear();
            }
        }
        if (!subBlocks.isEmpty()) factory.createWoodBlock(subBlocks);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("taxonomy", taxonomy).add("slabRegistry", slabRegistry).toString();
    }
}
