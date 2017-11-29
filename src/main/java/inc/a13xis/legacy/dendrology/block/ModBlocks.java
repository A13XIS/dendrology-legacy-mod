package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.content.PotionBrewingRecipe;
import inc.a13xis.legacy.dendrology.content.loader.TreeSpeciesLoader;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeBlockFactory;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeTaxonomy;
import inc.a13xis.legacy.dendrology.item.*;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.*;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import inc.a13xis.legacy.koresample.tree.item.DoorItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks
{
    private static boolean loaded = false;
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

    public static void loadOverWorldContent()
    {
        if(loaded){
            return;
        }
        TheMod.logger().info("Loading overworld species.");
        final TreeSpeciesLoader overworldContent = new TreeSpeciesLoader(overworldTaxonomy);
        overworldContent.load(new OverworldTreeBlockFactory());
        loaded = true;
    }

    public static Iterable<? extends DefinesLog> logDefinitions() { return overworldTaxonomy.logDefinitions(); }

    public static void registerAllBlocks(IForgeRegistry<Block> reg)
    {
        registerAllLogBlocks(reg);
        registerAllLeavesBlock(reg);
        registerAllSaplingBlocks(reg);
        registerAllWoodBlocks(reg);
        registerAllStairsBlocks(reg);
        registerAllDoorBlocks(reg);
        registerAllSingleSlabBlocks(reg);
        registerAllDoubleSlabBlocks(reg);
    }

    public static void registerAllItems(IForgeRegistry<Item> reg)
    {
        registerAllLogItems(reg);
        registerAllLeavesItems(reg);
        registerAllSaplingItems(reg);
        registerAllWoodItems(reg);
        registerAllStairsItems(reg);
        registerAllDoorItems(reg);
        registerAllSingleSlabItems(reg);
    }

    public static void registerAllItemRenders() {
        for (LogBlock logBlock : logBlocks) logBlock.registerItemModels();
        for (WoodBlock woodBlock : woodBlocks) woodBlock.registerItemModels();
        for (SlabBlock singleSlabBlock : singleSlabBlocks) singleSlabBlock.registerItemModels();
        for (StairsBlock stairsBlock : stairsBlocks) stairsBlock.registerItemModels();
        for (SaplingBlock saplingBlock : saplingBlocks) saplingBlock.registerItemModels();
        for (DoorBlock doorBlock : doorBlocks) doorBlock.registerItemModels();
        for (LeavesBlock leavesBlock : leavesBlocks) leavesBlock.registerItemModels();

    }

    private static void registerAllDoubleSlabBlocks(IForgeRegistry<Block> reg)
    {
        int slabCount = 0;
        for (final SlabBlock slab : doubleSlabBlocks)
        {
            String name = String.format("dslab%d", slabCount);
            reg.register(slab.setRegistryName(name));
            slabCount++;
        }
    }

    private static void registerAllLeavesBlock(IForgeRegistry<Block> reg)
    {
        int leavesCount = 0;
        for (final LeavesBlock leaves : leavesBlocks)
        {
            String name = String.format("leaves%d", leavesCount);
            leaves.setRegistryName(name);
            reg.register(leaves);
            Blocks.FIRE.setFireInfo(leaves, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
            leavesCount++;
        }
    }

    private static void registerAllLogBlocks(IForgeRegistry<Block> reg)
    {
        int logCount = 0;
        for (final LogBlock block : logBlocks)
        {
            String name = String.format("log%d", logCount);
            reg.register(block.setRegistryName(name));
            Blocks.FIRE.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
            logCount++;
        }
    }

    private static void registerAllSaplingBlocks(IForgeRegistry<Block> reg)
    {
        int saplingCount = 0;

        for (final SaplingBlock sapling : saplingBlocks)
        {
            String name = String.format("sapling%d", saplingCount);
            reg.register(sapling.setRegistryName(name));
            saplingCount++;
        }
    }

    private static void registerAllSingleSlabBlocks(IForgeRegistry<Block> reg)
    {
        int slabCount = 0;
        for (final SlabBlock slab : singleSlabBlocks)
        {
            String name = String.format("sslab%d", slabCount);
            reg.register(slab.setRegistryName(name));
            Blocks.FIRE.setFireInfo(slab, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
            slabCount++;
        }
    }

    private static void registerAllStairsBlocks(IForgeRegistry<Block> reg)
    {
        int stairsCount = 0;
        for (final StairsBlock stairs : stairsBlocks)
        {
            reg.register(stairs);
            Blocks.FIRE.setFireInfo(stairs, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
            stairsCount++;
        }
    }

    private static void registerAllDoorBlocks(IForgeRegistry<Block> reg)
    {
        for (final DoorBlock door : doorBlocks)
        {
            reg.register(door);
        }
    }

    private static void registerAllWoodBlocks(IForgeRegistry<Block> reg)

    {
        int woodBlockCount = 0;
        for (final WoodBlock wood : woodBlocks)
        {
            String name = String.format("wood%d", woodBlockCount);
            reg.register(wood.setRegistryName(name));
            Blocks.FIRE.setFireInfo(wood, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
            woodBlockCount++;
        }
    }

    private static void registerAllLeavesItems(IForgeRegistry<Item> reg)
    {
        int leavesCount = 0;
        for (final LeavesBlock leaves : leavesBlocks)
        {
            String name = String.format("leaves%d", leavesCount);
            reg.register(new ModLeavesItem(leaves).setRegistryName(name));
            leavesCount++;
        }
    }

    private static void registerAllLogItems(IForgeRegistry<Item> reg)
    {
        int logCount = 0;
        for (final LogBlock block : logBlocks)
        {
            String name = String.format("log%d", logCount);
            ImmutableList<String> subblockNames = block.getSubBlockNames();
            if(block instanceof  ModLogBlock){
                reg.register(new ModLogItem(block,(ModLogBlock)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            else if(block instanceof  ModLog2Block){
                reg.register(new ModLogItem(block,(ModLog2Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            else if(block instanceof  ModLog3Block){
                reg.register(new ModLogItem(block,(ModLog3Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            else{
                reg.register(new ModLogItem(block,(ModLog4Block)block,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            logCount++;
        }
    }

    private static void registerAllSaplingItems(IForgeRegistry<Item> reg)
    {
        int saplingCount = 0;

        for (final SaplingBlock sapling : saplingBlocks)
        {
            String name = String.format("sapling%d", saplingCount);
            List<String> subblockNames = sapling.subBlockNames();
            if(sapling instanceof  ModSaplingBlock){
                reg.register(new ModSaplingItem(sapling,(ModSaplingBlock)sapling,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            else{
                reg.register(new ModSaplingItem(sapling,(ModSapling2Block)sapling,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            }
            saplingCount++;
        }
    }

    private static void registerAllSingleSlabItems(IForgeRegistry<Item> reg)
    {
        int slabCount = 0;
        for (final SlabBlock slab : singleSlabBlocks)
        {
            String name = String.format("sslab%d", slabCount);
            if(slab instanceof  ModSlabBlock){
                reg.register(new ModSlabItem(slab,(ModSlabBlock) slab,(ModSlabBlock) doubleSlabBlocks.get(slabCount)).setRegistryName(name));
            }
            else{
                reg.register(new ModSlabItem(slab,(ModSlab2Block) slab,(ModSlab2Block) doubleSlabBlocks.get(slabCount)).setRegistryName(name));
            }
            slabCount++;
        }
    }

    private static void registerAllStairsItems(IForgeRegistry<Item> reg)
    {
        int stairsCount = 0;
        for (final StairsBlock stairs : stairsBlocks)
        {
            reg.register(new ItemBlock(stairs).setRegistryName(stairs.getRegistryName()));
            stairsCount++;
        }
    }

    private static void registerAllDoorItems(IForgeRegistry<Item> reg)
    {
        for (final DoorBlock door : doorBlocks)
        {
            reg.register(new DoorItem((DoorBlock) door).setRegistryName(door.getRegistryName()));
        }
    }

    private static void registerAllWoodItems(IForgeRegistry<Item> reg)

    {
        int woodBlockCount = 0;
        for (final WoodBlock wood : woodBlocks)
        {
            String name = String.format("wood%d", woodBlockCount);
            ImmutableList<String> subblockNames = wood.getSubBlockNames();
            reg.register(new ModWoodItem(wood,(ModWoodBlock)wood,subblockNames.toArray(new String[subblockNames.size()])).setRegistryName(name));
            woodBlockCount++;
        }
    }

    public static void loadBlock(LeavesBlock leavesBlock) { leavesBlocks.add(leavesBlock); }

    public static void loadBlock(LogBlock logBlock) { logBlocks.add(logBlock); }

    public static void loadBlock(SaplingBlock saplingBlock) { saplingBlocks.add(saplingBlock); }

    public static void loadBlock(SlabBlock singleSlabBlock, SlabBlock doubleSlabBlock)
    {
        singleSlabBlocks.add(singleSlabBlock);
        doubleSlabBlocks.add(doubleSlabBlock);
    }

    public static void loadBlock(StairsBlock stairsBlock) { stairsBlocks.add(stairsBlock); }

    public static void loadBlock(DoorBlock doorBlock) { doorBlocks.add(doorBlock); }

    public static void loadBlock(WoodBlock woodBlock) { woodBlocks.add(woodBlock); }

    public static Iterable<? extends DefinesSlab> slabDefinitions() { return overworldTaxonomy.slabDefinitions(); }

    public static Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return overworldTaxonomy.stairsDefinitions();
    }

    public static Iterable<? extends DefinesDoor> doorDefinitions()
    {
        return overworldTaxonomy.doorDefinitions();
    }

    public static OverworldTreeTaxonomy taxonomyInstance(){
        return overworldTaxonomy;
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

    public static Iterable<? extends BlockLog> logBlocks() { return ImmutableList.copyOf(logBlocks); }

    public static Iterable<? extends LeavesBlock> leavesBlocks() { return ImmutableList.copyOf(leavesBlocks); }

    public static Iterable<? extends WoodBlock> woodBlocks() { return ImmutableList.copyOf(woodBlocks); }

    public static Iterable<? extends SlabBlock> singleSlabBlocks() { return ImmutableList.copyOf(singleSlabBlocks); }

    public static Iterable<? extends StairsBlock> stairsBlocks() { return ImmutableList.copyOf(stairsBlocks); }

    public static Iterable<? extends SaplingBlock> saplingBlocks() { return ImmutableList.copyOf(saplingBlocks); }

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
