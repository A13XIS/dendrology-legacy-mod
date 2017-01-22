package inc.a13xis.legacy.dendrology.content.overworld;

import inc.a13xis.legacy.dendrology.block.*;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.common.util.slab.SingleDoubleSlab;
import inc.a13xis.legacy.koresample.tree.*;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;

public final class OverworldTreeBlockFactory implements TreeBlockFactory
{

    @Override
    public LeavesBlock createLeavesBlock(Iterable<DefinesLeaves> subBlocks)
    {
        final LeavesBlock block = new ModLeavesBlock(subBlocks);
        for (final DefinesLeaves subBlock : subBlocks)
            subBlock.assignLeavesBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public LogBlock createLogBlock(Iterable<DefinesLog> subBlocks)
    {
        final LogBlock block = new ModLogBlock(subBlocks);
        for (final DefinesLog subBlock : subBlocks)
            subBlock.assignLogBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public SaplingBlock createSaplingBlock(Iterable<DefinesSapling> subBlocks)
    {
        final SaplingBlock block = new ModSaplingBlock(subBlocks);
        for (final DefinesSapling subBlock : subBlocks)
            subBlock.assignSaplingBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public SingleDoubleSlab createSlabBlocks(Iterable<DefinesSlab> subBlocks)
    {
        final SlabBlock singleSlabBlock = new ModSlabBlock(false, subBlocks);
        final SlabBlock doubleSlabBlock = new ModSlabBlock(true, subBlocks);

        for (final DefinesSlab subBlock : subBlocks)
        {
            subBlock.assignSingleSlabBlock(singleSlabBlock);
            subBlock.assignDoubleSlabBlock(doubleSlabBlock);
        }

        ModBlocks.registerBlock(singleSlabBlock, doubleSlabBlock);
        return new SingleDoubleSlab(singleSlabBlock, doubleSlabBlock);
    }

    @Override
    public StairsBlock createStairsBlock(DefinesStairs definition)
    {
        final StairsBlock block = new ModStairsBlock(definition);
        block.setUnlocalizedName(String.format("stairs.%s", definition.stairsName()));

        definition.assignStairsBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public WoodBlock createWoodBlock(Iterable<DefinesWood> subBlocks)
    {
        final WoodBlock block = new ModWoodBlock(subBlocks);
        for (final DefinesWood subBlock : subBlocks)
            subBlock.assignWoodBlock(block);

        ModBlocks.registerBlock(block);
        return block;
    }
}
