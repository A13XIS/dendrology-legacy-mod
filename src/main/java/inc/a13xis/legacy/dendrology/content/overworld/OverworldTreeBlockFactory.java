package inc.a13xis.legacy.dendrology.content.overworld;

import inc.a13xis.legacy.dendrology.block.*;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
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
    private int typebit = 0;

    @Override
    public LeavesBlock createLeavesBlock(Iterable<DefinesLeaves> subBlocks)
    {
        final LeavesBlock block;
        if(typebit==3){
            block = new ModLeaves4Block(subBlocks);
        }
        else if(typebit == 2){
            block = new ModLeaves3Block(subBlocks);
        }
        else if(typebit ==1){
            block = new ModLeaves2Block(subBlocks);
        }
        else{
            block = new ModLeavesBlock(subBlocks);
        }
        for (final DefinesLeaves subBlock : subBlocks)
            subBlock.assignLeavesBlock(block);
        typebit++;
        ModBlocks.registerBlock(block);
        return block;
    }

    @Override
    public LogBlock createLogBlock(Iterable<DefinesLog> subBlocks)
    {
        final LogBlock block;
        if(typebit==3){
            block = new ModLog4Block(subBlocks);
        }
        else if(typebit == 2){
            block = new ModLog3Block(subBlocks);
        }
        else if(typebit ==1){
            block = new ModLog2Block(subBlocks);
        }
        else{
            block = new ModLogBlock(subBlocks);
        }
        for (final DefinesLog subBlock : subBlocks)
            subBlock.assignLogBlock(block);
        ModBlocks.registerBlock(block);
        typebit++;
        return block;
    }

    @Override
    public SaplingBlock createSaplingBlock(Iterable<DefinesSapling> subBlocks)
    {
        final SaplingBlock block;
        if(typebit >0){
            block = new ModSapling2Block(subBlocks);
        }
        else {
            block = new ModSaplingBlock(subBlocks);
        }
        for (final DefinesSapling subBlock : subBlocks)
            subBlock.assignSaplingBlock(block);
        ModBlocks.registerBlock(block);
        typebit++;
        return block;
    }

    @Override
    public SingleDoubleSlab createSlabBlocks(Iterable<DefinesSlab> subBlocks)
    {
        final SlabBlock singleSlabBlock;
        final SlabBlock doubleSlabBlock;
        if(typebit >0){
            singleSlabBlock = new ModSlab2Block(subBlocks);
            doubleSlabBlock = new ModSlab2Block(subBlocks);
        }
        else {
            singleSlabBlock = new ModSlabBlock(subBlocks);
            doubleSlabBlock = new ModSlabBlock(subBlocks);
        }

        for (final DefinesSlab subBlock : subBlocks)
        {
            subBlock.assignSingleSlabBlock(singleSlabBlock);
            subBlock.assignDoubleSlabBlock(doubleSlabBlock);
        }

        ModBlocks.registerBlock(singleSlabBlock, doubleSlabBlock);
        typebit++;
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
    public DoorBlock createDoorBlock(DefinesDoor definition)
    {
        final DoorBlock block = new ModDoorBlock(definition);

        definition.assignDoorBlock(block);

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

    public void resetTypeBit() {
        typebit = 0;
    }
}
