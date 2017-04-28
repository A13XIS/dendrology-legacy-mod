package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.content.PotionBrewingRecipe;
import inc.a13xis.legacy.dendrology.content.loader.TreeSpeciesLoader;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeBlockFactory;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeTaxonomy;
import inc.a13xis.legacy.dendrology.item.*;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.common.block.FenceBlock;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.*;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.PotionItems;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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
    private static final List<DoorBlock> doorBlocks = Lists.newArrayList();
    private static final List<SaplingBlock> saplingBlocks = Lists.newArrayList();
    private static final List<LeavesBlock> leavesBlocks = Lists.newArrayList();
    private static final OverworldTreeTaxonomy overworldTaxonomy = new OverworldTreeTaxonomy();
    private static final List<MixRecipe> recipes = new ArrayList<>();

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
        registerAllDoorBlocks();
        registerAllSingleSlabBlocks();
        registerAllDoubleSlabBlocks();
    }

    public static void registerAllBlockRenders() {
        for (LogBlock logBlock : logBlocks) logBlock.registerBlockModels();
        for (WoodBlock woodBlock : woodBlocks) woodBlock.registerBlockModels();
        for (SlabBlock singleSlabBlock : singleSlabBlocks) singleSlabBlock.registerBlockModels();
        for (StairsBlock stairsBlock : stairsBlocks) stairsBlock.registerBlockModel();
        for (DoorBlock doorBlock : doorBlocks) ((ModDoorBlock)doorBlock).registerBlockModel();
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

    private static void registerAllDoorBlocks()
    {
        int doorCount = 0;
        for (final DoorBlock door : doorBlocks)
        {
            registerDoorBlock(door, String.format("door%d", doorCount));
            doorCount++;
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

    public static void registerBlock(DoorBlock doorBlock) { doorBlocks.add(doorBlock); }

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

    private static void registerDoorBlock(Block block, String name)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ModDoorItem((DoorBlock) block).setRegistryName(block.getRegistryName()));
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

    public static Iterable<? extends DefinesDoor> doorDefinitions()
    {
        return overworldTaxonomy.doorDefinitions();
    }

    public static Iterable<? extends DefinesDoor> fenceDefinitions(){ return overworldTaxonomy.doorDefinitions();}

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

    public static void registerPotionEffects() {
        ArrayList<MixRecipe> list = new ArrayList<>();
        for(DefinesSapling define:overworldTaxonomy.saplingDefinitions()){
            if(define.saplingBlock() instanceof ModSaplingBlock){
                switch(((ModSlabBlock.EnumType)define.saplingSubBlockVariant())){
                    case EWCALY:{
                        ItemStack sapling = new ItemStack(Item.getItemFromBlock(define.saplingBlock()),1,define.saplingSubBlockVariant().ordinal());
                        ItemStack frompotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.WATER);
                        ItemStack topotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.MUNDANE);
                        list.add(new MixRecipe(frompotion,sapling,topotion));
                        frompotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.AWKWARD);
                        topotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.POISON);
                        list.add(new MixRecipe(frompotion,sapling,topotion));
                    break;}
                    case KIPARIS:{
                        ItemStack sapling = new ItemStack(Item.getItemFromBlock(define.saplingBlock()),1,define.saplingSubBlockVariant().ordinal());
                        ItemStack frompotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.WATER);
                        ItemStack topotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.MUNDANE);
                        list.add(new MixRecipe(frompotion,sapling,topotion));
                        frompotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.AWKWARD);
                        topotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),PotionTypes.SWIFTNESS);
                        list.add(new MixRecipe(frompotion,sapling,topotion));
                    break;}
                }
            }
        }
        for(MixRecipe mr:list) {
            if (!recipes.contains(mr)){
                BrewingRecipeRegistry.addRecipe(new PotionBrewingRecipe(PotionUtils.getPotionFromItem(mr.getFrom()), mr.getConversator(), PotionUtils.getPotionFromItem(mr.getTo())));
                recipes.add(mr);
            }
        }
    }

    public static DoorBlock getDoorBlock(int position){
        return doorBlocks.get(position);
    }

    private static class MixRecipe {
        private final ItemStack from, to, conversator;
        MixRecipe(ItemStack from, ItemStack conversator, ItemStack to){
            if(!(from.getItem() instanceof ItemPotion)||!(to.getItem() instanceof ItemPotion)){
                throw new IllegalArgumentException("Both \"from\" and \"to\" have to be an item potion stack");
            }
            this.from=from;
            this.to=to;
            this.conversator=conversator;
        }

        public ItemStack getFrom() {
            return from;
        }

        public ItemStack getTo() {
            return to;
        }

        public ItemStack getConversator() {
            return conversator;
        }

        @Override
        public boolean equals(Object other){
            if(!(other instanceof MixRecipe)){
                return false;
            }
            MixRecipe omr=(MixRecipe)other;
            boolean test = from.getItem()==omr.from.getItem()&&conversator.getItem()==omr.conversator.getItem()&&to.getItem()==omr.to.getItem();
            boolean test2 = PotionUtils.getPotionFromItem(from)==PotionUtils.getPotionFromItem(omr.from)&&conversator.getItemDamage()==omr.conversator.getItemDamage()&&PotionUtils.getPotionFromItem(to)==PotionUtils.getPotionFromItem(omr.to);
            return test && test2;
        }
    }
}
