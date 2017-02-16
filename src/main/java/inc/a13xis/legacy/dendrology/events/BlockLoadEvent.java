package inc.a13xis.legacy.dendrology.events;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.block.ModSaplingBlock;
import inc.a13xis.legacy.dendrology.block.ModSlabBlock;
import inc.a13xis.legacy.dendrology.content.PotionBrewingRecipe;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lexis on 16.02.2017.
 */
public class BlockLoadEvent {
    private static final int DEFAULT_LEAVES_FIRE_ENCOURAGEMENT = 30;
    private static final int DEFAULT_LOG_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_PLANKS_FIRE_ENCOURAGEMENT = 5;
    private static final int DEFAULT_STAIRS_FIRE_ENCOURAGEMENT = DEFAULT_PLANKS_FIRE_ENCOURAGEMENT;
    private static final int DEFAULT_LEAVES_FLAMMABILITY = 60;
    private static final int DEFAULT_LOG_FLAMMABILITY = 5;
    private static final int DEFAULT_PLANKS_FLAMMABILITY = 20;
    private static final int DEFAULT_STAIRS_FLAMMABILITY = DEFAULT_PLANKS_FLAMMABILITY;
    private static final List<MixRecipe> recipes = new ArrayList<>();

    static IForgeRegistry<Block> blockreg =null;
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        blockreg =event.getRegistry();
        registerAllDoubleSlabBlocks();
        registerAllSingleSlabBlocks();
        registerAllLeavesBlocks();
        registerAllLogBlocks();
        registerAllSaplingBlocks();
        registerAllStairsBlocks();
        registerAllWoodBlocks();
        blockreg =null;
        registerPotionEffects();
    }

    private static void registerAllDoubleSlabBlocks()
    {
        int slabCount = 0;
        for (final SlabBlock slab : ModBlocks.doubleSlabBlocks())
        {
            registerDSlabBlock(slab, String.format("dslab%d", slabCount));
            slabCount++;
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

    private static void registerAllSingleSlabBlocks()
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
        block.setRegistryName(name);
        blockreg.register(block);
        Blocks.FIRE.setFireInfo(block, DEFAULT_LEAVES_FIRE_ENCOURAGEMENT, DEFAULT_LEAVES_FLAMMABILITY);
    }

    private static void registerLogBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        block.setRegistryName(name);
        blockreg.register(block);
        Blocks.FIRE.setFireInfo(block, DEFAULT_LOG_FIRE_ENCOURAGEMENT, DEFAULT_LOG_FLAMMABILITY);
    }

    private static void registerSaplingBlock(Block block, String name, List<String> subblockNames)
    {
        block.setRegistryName(name);
        blockreg.register(block);
    }

    private static void registerSlabBlock(Block block, String name, SlabBlock singleSlab, SlabBlock doubleSlab)
    {
        block.setRegistryName(name);
        blockreg.register(block);
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerDSlabBlock(Block b,String name){
        b.setRegistryName(name);
        blockreg.register(b);
    }

    private static void registerStairsBlock(Block block, String name)
    {
        block.setRegistryName(name);
        blockreg.register(block);
        Blocks.FIRE.setFireInfo(block, DEFAULT_STAIRS_FIRE_ENCOURAGEMENT, DEFAULT_STAIRS_FLAMMABILITY);
    }

    private static void registerWoodBlock(Block block, String name, ImmutableList<String> subblockNames)
    {
        block.setRegistryName(name);
        blockreg.register(block);
        Blocks.FIRE.setFireInfo(block, DEFAULT_PLANKS_FIRE_ENCOURAGEMENT, DEFAULT_PLANKS_FLAMMABILITY);
    }

    public static void registerPotionEffects() {
        ArrayList<MixRecipe> list = new ArrayList<>();
        for(DefinesSapling define:ModBlocks.taxonomyInstance().saplingDefinitions()){
            if(define.saplingBlock() instanceof ModSaplingBlock){
                switch(((ModSlabBlock.EnumType)define.saplingSubBlockVariant())){
                    case EWCALY:{
                        ItemStack sapling = new ItemStack(Item.getItemFromBlock(define.saplingBlock()),1,define.saplingSubBlockVariant().ordinal());
                        ItemStack frompotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0), PotionTypes.WATER);
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
