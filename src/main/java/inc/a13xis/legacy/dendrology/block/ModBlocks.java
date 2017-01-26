package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
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
import inc.a13xis.legacy.dendrology.content.loader.TreeSpeciesLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    @SuppressWarnings("MethodWithMultipleLoops")
    private static void addAllSaplingsToChests()
    {
        TheMod.logger().info("Hiding saplings in chests.");
        final Settings settings = Settings.INSTANCE;

        for (final DefinesSapling saplingDefinition : overworldTaxonomy.saplingDefinitions())
            for (final String chestType : Settings.chestTypes())
                addSaplingToChest(saplingDefinition, chestType, settings.chestRarity(chestType));
    }

    private static void addSaplingToChest(DefinesSapling saplingDefinition, String chestType, int rarity)
    {
        if (rarity <= 0) return;

        final ItemStack sapling =
                new ItemStack(saplingDefinition.saplingBlock(), 1, saplingDefinition.saplingSubBlockVariant().ordinal());
        final WeightedRandomChestContent chestContent = new WeightedRandomChestContent(sapling, 1, 2, rarity);

        final ChestGenHooks chestGenInfo = ChestGenHooks.getInfo(chestType);
        chestGenInfo.addItem(chestContent);
    }

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

    public static void registerAllRenders() {
        for(LogBlock block:logBlocks){
            block.registerBlockModels();
        }
        for(WoodBlock block:woodBlocks){
            block.registerBlockModels();
        }
        for(SlabBlock block:singleSlabBlocks){
            block.registerBlockModels();
        }
        for(SlabBlock block:doubleSlabBlocks){
            block.registerBlockModels();
        }
        for(StairsBlock block:stairsBlocks){
            block.registerBlockModel();
        }
        for(SaplingBlock block:saplingBlocks){
            block.registerBlockModels();
        }
        for(LeavesBlock block:leavesBlocks){
            block.registerBlockModels();
        }
    }

    private static void registerAllDoubleSlabBlocks()
    {
        int slabCount = 0;
        for (final SlabBlock slab : doubleSlabBlocks)
        {
            registerSlabBlock(slab, String.format("dslab%d", slabCount), singleSlabBlocks.get(slabCount), slab, true);
            slabCount++;
        }
    }

    private static void registerAllLeavesBlock()
    {
        int leavesCount = 0;
        for (final Block block : leavesBlocks)
        {
            registerLeavesBlock(block, String.format("leaves%d", leavesCount));
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
            registerSlabBlock(slab, String.format("sslab%d", slabCount), slab, doubleSlabBlocks.get(slabCount), false);
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
            registerWoodBlock(wood, String.format("planks%d", woodBlockCount), wood.getSubBlockNames());
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
        GameRegistry.registerBlock(block, ModLeavesItem.class, name);
        Blocks.fire.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, ModLogItem.class, name, block, subblockNames.toArray(new String[subblockNames.size()]));
        Blocks.fire.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
    }

    private static void registerSaplingBlock(Block block, String name, List<String> subblockNames)
    {
        GameRegistry.registerBlock(block, ModSaplingItem.class, name, block, subblockNames.toArray(new String[subblockNames.size()]));
    }

    private static void registerSlabBlock(Block block, String name, SlabBlock singleSlab, SlabBlock doubleSlab,
                                          boolean unused)
    {
        GameRegistry.registerBlock(block, ModSlabItem.class, name, singleSlab, doubleSlab);
    }

    private static void registerStairsBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
        Blocks.fire.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerWoodBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        GameRegistry.registerBlock(block, ModWoodItem.class, name, block, subblockNames.toArray(new String[subblockNames.size()]));
        Blocks.fire.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
    }

    public static Iterable<? extends SaplingBlock> saplingBlocks() { return ImmutableList.copyOf(saplingBlocks); }

    public static Iterable<? extends Block> singleSlabBlocks() { return ImmutableList.copyOf(singleSlabBlocks); }

    public static Iterable<? extends DefinesSlab> slabDefinitions() { return overworldTaxonomy.slabDefinitions(); }

    public static Iterable<? extends Block> stairsBlocks() { return ImmutableList.copyOf(stairsBlocks); }

    public static Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return overworldTaxonomy.stairsDefinitions();
    }

    public static Iterable<? extends Block> woodBlocks() { return ImmutableList.copyOf(woodBlocks); }

    @SuppressWarnings("MethodMayBeStatic")
    public void loadContent()
    {
        loadOverWorldContent();
        registerAllBlocks();
        addAllSaplingsToChests();
    }

    public static void initAllSubRenders() {
        for(ModWoodBlock.EnumType type : ModWoodBlock.EnumType.values()) {
            for (LogBlock block : logBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
            for (WoodBlock block : woodBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
            for (SlabBlock block : singleSlabBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
            for (SlabBlock block : doubleSlabBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
            for (SaplingBlock block : saplingBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
            for (LeavesBlock block : leavesBlocks) {
                ModelBakery.addVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName().substring(block.getUnlocalizedName().indexOf('.')+1) + "_" + type.getName());
            }
        }
    }
}
