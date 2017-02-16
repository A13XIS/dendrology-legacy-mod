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

    private static final List<LogBlock> logBlocks = Lists.newArrayList();
    private static final List<WoodBlock> woodBlocks = Lists.newArrayList();
    private static final List<SlabBlock> singleSlabBlocks = Lists.newArrayList();
    private static final List<SlabBlock> doubleSlabBlocks = Lists.newArrayList();
    private static final List<StairsBlock> stairsBlocks = Lists.newArrayList();
    private static final List<SaplingBlock> saplingBlocks = Lists.newArrayList();
    private static final List<LeavesBlock> leavesBlocks = Lists.newArrayList();
    private static final OverworldTreeTaxonomy overworldTaxonomy = new OverworldTreeTaxonomy();

    public static Iterable<? extends LeavesBlock> leavesBlocks() { return ImmutableList.copyOf(leavesBlocks); }

    private static void loadOverWorldContent()
    {
        TheMod.logger().info("Loading overworld species.");
        final TreeSpeciesLoader overworldContent = new TreeSpeciesLoader(overworldTaxonomy);
        overworldContent.load(new OverworldTreeBlockFactory());
    }

    public static Iterable<? extends LogBlock> logBlocks() { return ImmutableList.copyOf(logBlocks); }

    public static Iterable<? extends DefinesLog> logDefinitions() { return overworldTaxonomy.logDefinitions(); }

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

    public static Iterable<? extends SaplingBlock> saplingBlocks() { return ImmutableList.copyOf(saplingBlocks); }

    public static Iterable<? extends SlabBlock> singleSlabBlocks() { return ImmutableList.copyOf(singleSlabBlocks); }

    public static ImmutableList<? extends SlabBlock> doubleSlabBlocks() { return ImmutableList.copyOf(doubleSlabBlocks); }

    public static Iterable<? extends DefinesSlab> slabDefinitions() { return overworldTaxonomy.slabDefinitions(); }

    public static Iterable<? extends StairsBlock> stairsBlocks() { return ImmutableList.copyOf(stairsBlocks); }

    public static Iterable<? extends DefinesStairs> stairsDefinitions()
    {
        return overworldTaxonomy.stairsDefinitions();
    }

    public static OverworldTreeTaxonomy taxonomyInstance(){
        return overworldTaxonomy;
    }

    public static Iterable<? extends WoodBlock> woodBlocks() { return ImmutableList.copyOf(woodBlocks); }

    @SuppressWarnings("MethodMayBeStatic")
    public void loadContent()
    {
        loadOverWorldContent();
    }

}
