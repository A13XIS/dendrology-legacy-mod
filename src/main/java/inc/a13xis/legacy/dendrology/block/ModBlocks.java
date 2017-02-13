package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.content.loader.TreeSpeciesLoader;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeBlockFactory;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeTaxonomy;
import inc.a13xis.legacy.dendrology.item.*;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks
{
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_PLANKS_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_STAIRS_FIRE_ENCOURAGEMENT = DEFAULT_PLANKS_FIRE_ENCOURAGEMENT;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;
    private static final int DEFAULT_PLANKS_FLAMMABILITY = 20;
    private static final int DEFAULT_STAIRS_FLAMMABILITY = DEFAULT_PLANKS_FLAMMABILITY;
    private static final List<LogBlock> logBlocks = Lists.newArrayList();
    private static final List<WoodBlock> woodBlocks = Lists.newArrayList();
    private static final List<SlabBlock> singleSlabBlocks = Lists.newArrayList();
    private static final List<SlabBlock> doubleSlabBlocks = Lists.newArrayList();
    private static final List<StairsBlock> stairsBlocks = Lists.newArrayList();
    private static final List<SaplingBlock> saplingBlocks = Lists.newArrayList();
    private static final List<LeavesBlock> leavesBlocks = Lists.newArrayList();
    private static final OverworldTreeTaxonomy overworldTaxonomy = new OverworldTreeTaxonomy();
    private static ArrayList<ItemBlock> registered = new ArrayList<ItemBlock>();

    public static Iterable<? extends LeavesBlock> leavesBlocks() { return ImmutableList.copyOf(leavesBlocks); }

    private static void loadOverWorldContent()
    {
        TheMod.logger().info("Loading overworld species.");
        final TreeSpeciesLoader overworldContent = new TreeSpeciesLoader(overworldTaxonomy);
        overworldContent.load(new OverworldTreeBlockFactory());
    }

    public static Iterable<? extends BlockLog> logBlocks() { return ImmutableList.copyOf(logBlocks); }

    public static Iterable<? extends DefinesLog> logDefinitions() { return overworldTaxonomy.logDefinitions(); }

    private static void registerAllBlocks()
    {
        registerAllLogBlocks();
        registerAllLeavesBlock();
        registerAllSaplingBlocks();
        registerAllWoodBlocks();
        registerAllStairsBlocks();
        registerAllSingleSlabBlocks();
        registerAllDoubleSlabBlocks();
    }

    public static void registerAllBlockRenders() {
        for (LogBlock logBlock : logBlocks) logBlock.registerBlockModels();
        for (WoodBlock woodBlock : woodBlocks) woodBlock.registerBlockModels();
        for (SlabBlock singleSlabBlock : singleSlabBlocks) singleSlabBlock.registerBlockModels();
        for (StairsBlock stairsBlock : stairsBlocks) stairsBlock.registerBlockModel();
        for (SaplingBlock saplingBlock : saplingBlocks) saplingBlock.registerBlockModels();
        for (LeavesBlock leavesBlock : leavesBlocks) leavesBlock.registerBlockModels();
    }

    private static void registerAllDoubleSlabBlocks()
    {
        int slabCount = 0;
        for (final SlabBlock slab : doubleSlabBlocks)
        {
            registerDSlabBlock(slab, String.format("dslab%d", slabCount));
            slabCount++;
        }
    }

    private static void registerAllLeavesBlock()
    {
        int leavesCount = 3;
        for (final LeavesBlock leaves : leavesBlocks)
        {
            registerLeavesBlock(leaves, String.format(LeavesBlock.getRawUnlocalizedName(leaves)+"%d", leavesCount));
            leavesCount++;
        }
    }

    private static void registerAllLogBlocks()
    {
        int logCount = 0;
        for (final LogBlock block : logBlocks)
        {
            registerLogBlock(block, String.format("log%d", logCount), block.getSubBlockNames());
            logCount++;
        }
    }

    private static void registerAllSaplingBlocks()
    {
        int saplingCount = 0;

        for (final SaplingBlock sapling : saplingBlocks)
        {
            registerSaplingBlock(sapling, String.format("sapling%d", saplingCount), sapling.subBlockNames());
            saplingCount++;
        }
    }

    private static void registerAllSingleSlabBlocks()
    {
        int slabCount = 0;
        for (final SlabBlock slab : singleSlabBlocks)
        {
            registerSlabBlock(slab, String.format("sslab%d", slabCount), slab, doubleSlabBlocks.get(slabCount));
            slabCount++;
        }
    }

    private static void registerAllStairsBlocks()
    {
        int stairsCount = 0;
        for (final StairsBlock stairs : stairsBlocks)
        {
            registerStairsBlock(stairs, String.format("stairs%d", stairsCount));
            stairsCount++;
        }
    }


    private static void registerAllWoodBlocks()

    {
        int woodBlockCount = 0;
        for (final WoodBlock wood : woodBlocks)
        {
            registerWoodBlock(wood, String.format(WoodBlock.getRawUnlocalizedName(wood)+"%d", woodBlockCount), wood.getSubBlockNames());
            woodBlockCount++;
        }
    }


    public static void registerBlock(LeavesBlock leavesBlock) { leavesBlocks.add(leavesBlock); }

    public static void registerBlock(LogBlock logBlock) { logBlocks.add(logBlock); }

    public static void registerBlock(SaplingBlock saplingBlock) { saplingBlocks.add(saplingBlock); }

    public static void registerBlock(SlabBlock singleSlabBlock, SlabBlock doubleSlabBlock)
    {
        singleSlabBlocks.add(singleSlabBlock);
        doubleSlabBlocks.add(doubleSlabBlock);
    }

    public static void registerBlock(StairsBlock stairsBlock) { stairsBlocks.add(stairsBlock); }

    public static void registerBlock(WoodBlock woodBlock) { woodBlocks.add(woodBlock); }

    private static void registerLeavesBlock(Block block, String name)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ModLeavesItem(block).setRegistryName(block.getRegistryName()));
        Blocks.FIRE.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        if(block instanceof  ModLogBlock){
            GameRegistry.register(new ModLogItem(block,(ModLogBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else if(block instanceof  ModLog2Block){
            GameRegistry.register(new ModLogItem(block,(ModLog2Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else if(block instanceof  ModLog3Block){
            GameRegistry.register(new ModLogItem(block,(ModLog3Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else{
            GameRegistry.register(new ModLogItem(block,(ModLog4Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        Blocks.FIRE.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
    }

    private static void registerSaplingBlock(Block block, String name, List<String> subblockNames)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        if(block instanceof  ModSaplingBlock){
            GameRegistry.register(new ModSaplingItem(block,(ModSaplingBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else{
            GameRegistry.register(new ModSaplingItem(block,(ModSapling2Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
    }

    private static void registerSlabBlock(Block block, String name, SlabBlock singleSlab, SlabBlock doubleSlab)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        if(block instanceof  ModSlabBlock){
            GameRegistry.register(new ModSlabItem(block,(ModSlabBlock) singleSlab,(ModSlabBlock) doubleSlab).setRegistryName(block.getRegistryName()));
        }
        else{
            GameRegistry.register(new ModSlabItem(block,(ModSlab2Block) singleSlab,(ModSlab2Block) doubleSlab).setRegistryName(block.getRegistryName()));
        }
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerDSlabBlock(Block b,String name){
        b.setRegistryName(name);
        GameRegistry.register(b);
    }

    private static void registerStairsBlock(Block block, String name)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerWoodBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ModWoodItem(block,(ModWoodBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        Blocks.FIRE.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
    }

    public static Iterable<? extends SaplingBlock> saplingBlocks() { return ImmutableList.copyOf(saplingBlocks); }

    public static Iterable<? extends Block> singleSlabBlocks() { return ImmutableList.copyOf(singleSlabBlocks); }

    public static Iterable<? extends DefinesSlab> slabDefinitions() { return overworldTaxonomy.slabDefinitions(); }

    public static Iterable<? extends Block> stairsBlocks() { return ImmutableList.copyOf(stairsBlocks); }

    public static Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return overworldTaxonomy.stairsDefinitions();
    }

    public static OverworldTreeTaxonomy taxonomyInstance(){
        return overworldTaxonomy;
    }

    public static Iterable<? extends Block> woodBlocks() { return ImmutableList.copyOf(woodBlocks); }

    @SuppressWarnings("MethodMayBeStatic")
    public void loadContent()
    {
        loadOverWorldContent();
        registerAllBlocks();
    }
}
