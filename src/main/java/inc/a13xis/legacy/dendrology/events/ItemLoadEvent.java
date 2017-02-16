package inc.a13xis.legacy.dendrology.events;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.*;
import inc.a13xis.legacy.dendrology.content.crafting.OreDictHandler;
import inc.a13xis.legacy.dendrology.content.crafting.Smelter;
import inc.a13xis.legacy.dendrology.item.*;
import inc.a13xis.legacy.dendrology.world.Colorizer;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import java.util.List;

/**
 * Created by Lexis on 16.02.2017.
 */
public class ItemLoadEvent {
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_PLANKS_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_STAIRS_FIRE_ENCOURAGEMENT = DEFAULT_PLANKS_FIRE_ENCOURAGEMENT;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;
    private static final int DEFAULT_PLANKS_FLAMMABILITY = 20;
    private static final int DEFAULT_STAIRS_FLAMMABILITY = DEFAULT_PLANKS_FLAMMABILITY;
    
    static IForgeRegistry<Item> ireg =null;
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Item> event)
    {
        ireg =event.getRegistry();
        registerAllSlabBlocks();
        registerAllLeavesBlocks();
        registerAllLogBlocks();
        registerAllSaplingBlocks();
        registerAllStairsBlocks();
        registerAllWoodBlocks();
        ireg =null;
        new OreDictHandler().registerBlocksWithOreDictinary();
        new Smelter().registerSmeltings();
        for(DefinesLeaves define: ModBlocks.taxonomyInstance().leavesDefinitions()){
            Colorizer.registerBlockColor(define.leavesBlock());
            Colorizer.registerItemBlockColor(define.leavesBlock());
        }
    }

    private static void registerAllLeavesBlocks()
    {
        int leavesCount = 3;
        for (final LeavesBlock leaves : ModBlocks.leavesBlocks())
        {
            registerLeavesBlock(leaves, String.format(LeavesBlock.getRawUnlocalizedName(leaves)+"%d", leavesCount));
            leavesCount++;
        }
    }

    private static void registerAllLogBlocks()
    {
        int logCount = 0;
        for (final LogBlock block : ModBlocks.logBlocks())
        {
            registerLogBlock(block, String.format("log%d", logCount), block.getSubBlockNames());
            logCount++;
        }
    }

    private static void registerAllSaplingBlocks()
    {
        int saplingCount = 0;

        for (final SaplingBlock sapling : ModBlocks.saplingBlocks())
        {
            registerSaplingBlock(sapling, String.format("sapling%d", saplingCount), sapling.subBlockNames());
            saplingCount++;
        }
    }

    private static void registerAllSlabBlocks()
    {
        int slabCount = 0;
        for (final SlabBlock slab : ModBlocks.singleSlabBlocks())
        {
            registerSlabBlock(slab, String.format("sslab%d", slabCount), slab, ModBlocks.doubleSlabBlocks().get(slabCount));
            slabCount++;
        }
    }

    private static void registerAllStairsBlocks()
    {
        int stairsCount = 0;
        for (final StairsBlock stairs : ModBlocks.stairsBlocks())
        {
            registerStairsBlock(stairs, String.format("stairs%d", stairsCount));
            stairsCount++;
        }
    }


    private static void registerAllWoodBlocks()

    {
        int woodBlockCount = 0;
        for (final WoodBlock wood : ModBlocks.woodBlocks())
        {
            registerWoodBlock(wood, String.format(WoodBlock.getRawUnlocalizedName(wood)+"%d", woodBlockCount), wood.getSubBlockNames());
            woodBlockCount++;
        }
    }

    private static void registerLeavesBlock(Block block, String name)
    {
        ireg.register(new ModLeavesItem(block).setRegistryName(block.getRegistryName()));
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        if(block instanceof ModLogBlock){
            ireg.register(new ModLogItem(block,(ModLogBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else if(block instanceof ModLog2Block){
            ireg.register(new ModLogItem(block,(ModLog2Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else if(block instanceof ModLog3Block){
            ireg.register(new ModLogItem(block,(ModLog3Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else{
            ireg.register(new ModLogItem(block,(ModLog4Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
    }

    private static void registerSaplingBlock(Block block, String name, List<String> subblockNames)
    {
        if(block instanceof  ModSaplingBlock){
            ireg.register(new ModSaplingItem(block,(ModSaplingBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
        else{
            ireg.register(new ModSaplingItem(block,(ModSapling2Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        }
    }

    private static void registerSlabBlock(Block block, String name, SlabBlock singleSlab, SlabBlock doubleSlab)
    {
        if(block instanceof  ModSlabBlock){
            ireg.register(new ModSlabItem(block,(ModSlabBlock) singleSlab,(ModSlabBlock) doubleSlab).setRegistryName(block.getRegistryName()));
        }
        else{
            ireg.register(new ModSlabItem(block,(ModSlab2Block) singleSlab,(ModSlab2Block) doubleSlab).setRegistryName(block.getRegistryName()));
        }
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerStairsBlock(Block block, String name)
    {
        ireg.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerWoodBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        ireg.register(new ModWoodItem(block,(ModWoodBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(block.getRegistryName()));
        Blocks.FIRE.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
    }
}
