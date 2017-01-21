package inc.a13xis.legacy.dendrology.content.overworld;


import inc.a13xis.legacy.dendrology.content.ProvidesPotionEffect;
import inc.a13xis.legacy.dendrology.world.AcemusColorizer;
import inc.a13xis.legacy.dendrology.world.CerasuColorizer;
import inc.a13xis.legacy.dendrology.world.KulistColorizer;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.AcemusTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.CedrumTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.CerasuTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.DelnasTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.EwcalyTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.HekurTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.KiparisTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.KulistTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.LataTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.NucisTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.PorfforTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.SalyxTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.TuopaTree;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import inc.a13xis.legacy.koresample.tree.DefinesTree;
import inc.a13xis.legacy.koresample.tree.DefinesWood;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.gen.feature.WorldGenerator;

import static com.google.common.base.Preconditions.*;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.ACEMUS_COLOR;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.BASIC_COLOR;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.CERASU_COLOR;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.KULIST_COLOR;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.NO_COLOR;

@SuppressWarnings({ "NonSerializableFieldInSerializableClass", "ClassHasNoToStringMethod" })
public enum OverworldTreeSpecies
        implements DefinesLeaves, DefinesLog, DefinesSapling, DefinesSlab, DefinesStairs, DefinesTree, DefinesWood,
        ProvidesPotionEffect
{
    // REORDERING WILL CAUSE DAMAGE TO SAVES
    ACEMUS(ACEMUS_COLOR, new AcemusTree(), new AcemusTree(false)),
    CEDRUM(NO_COLOR, new CedrumTree(), new CedrumTree(false)),
    CERASU(CERASU_COLOR, new CerasuTree(), new CerasuTree(false)),
    DELNAS(NO_COLOR, new DelnasTree(), new DelnasTree(false)),
    EWCALY(NO_COLOR, new EwcalyTree(), new EwcalyTree(false), PotionHelper.sugarEffect),
    HEKUR(BASIC_COLOR, new HekurTree(), new HekurTree(false)),
    KIPARIS(NO_COLOR, new KiparisTree(), new KiparisTree(false), PotionHelper.spiderEyeEffect),
    KULIST(KULIST_COLOR, new KulistTree(), new KulistTree(false)),
    LATA(BASIC_COLOR, new LataTree(), new LataTree(false)),
    NUCIS(BASIC_COLOR, new NucisTree(), new NucisTree(false)),
    PORFFOR(NO_COLOR, new PorfforTree(), new PorfforTree(false)),
    SALYX(NO_COLOR, new SalyxTree(), new SalyxTree(false)),
    TUOPA(BASIC_COLOR, new TuopaTree(), new TuopaTree(false));

    private final AbstractTree saplingTreeGen;
    private final AbstractTree worldTreeGen;
    private final Colorizer colorizer;
    private final String potionEffect;

    private int leavesMeta;
    private int logMeta;
    private int planksMeta;
    private int saplingMeta;
    private int slabMetadata;

    private SlabBlock doubleSlabBlock = null;
    private LeavesBlock leavesBlock = null;
    private LogBlock logBlock = null;
    private WoodBlock woodBlock = null;
    private SaplingBlock saplingBlock = null;
    private SlabBlock singleSlabBlock = null;
    private StairsBlock stairsBlock = null;

    static
    {
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
        {
            tree.saplingTreeGen.setTree(tree);
            tree.worldTreeGen.setTree(tree);
        }
    }

    OverworldTreeSpecies(Colorizer colorizer, AbstractTree saplingTreeGen, AbstractTree worldTreeGen,
                         String potionEffect)
    {
        this.colorizer = colorizer;
        this.saplingTreeGen = saplingTreeGen;
        this.worldTreeGen = worldTreeGen;
        this.potionEffect = potionEffect;
    }

    OverworldTreeSpecies(Colorizer colorizer, AbstractTree saplingTreeGen, AbstractTree worldTreeGen)
    {
        this(colorizer, saplingTreeGen, worldTreeGen, null);
    }

    @Override
    public String potionEffect() { return potionEffect; }

    @Override
    @SideOnly(Side.CLIENT)
    public int getLeavesInventoryColor()
    {
        switch (colorizer)
        {
            case NO_COLOR:
                return 0xffffff;
            case ACEMUS_COLOR:
                return AcemusColorizer.getInventoryColor();
            case CERASU_COLOR:
                return CerasuColorizer.getInventoryColor();
            case KULIST_COLOR:
                return KulistColorizer.getInventoryColor();
            default:
                return Blocks.leaves.getRenderColor(Blocks.leaves.getBlockState().getBaseState());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getLeavesColor(IBlockAccess blockAccess, BlockPos pos)
    {
        switch (colorizer)
        {
            case NO_COLOR:
                return 0xffffff;
            case ACEMUS_COLOR:
                return AcemusColorizer.getColor(pos);
            case CERASU_COLOR:
                return CerasuColorizer.getColor(pos);
            case KULIST_COLOR:
                return KulistColorizer.getColor(pos);
            default:
                return Blocks.leaves.colorMultiplier(blockAccess, pos);
        }
    }

    @Override
    public void assignLeavesBlock(LeavesBlock leavesBlock)
    {
        checkState(this.leavesBlock == null);
        this.leavesBlock = leavesBlock;
    }

    @Override
    public void assignLeavesSubBlockIndex(int leavesMeta) { this.leavesMeta = leavesMeta; }

    @Override
    public LeavesBlock leavesBlock()
    {
        checkState(leavesBlock != null);
        return leavesBlock;
    }

    @Override
    public int leavesSubBlockIndex() { return leavesMeta; }

    @SuppressWarnings("ReturnOfThis")
    @Override
    public DefinesSapling saplingDefinition() { return this; }

    @Override
    public String speciesName() { return name().toLowerCase(); }

    @Override
    public void assignLogBlock(LogBlock logBlock)
    {
        checkState(this.logBlock == null);
        this.logBlock = logBlock;
    }

    @Override
    public void assignLogSubBlockIndex(int logMeta) { this.logMeta = logMeta; }

    @Override
    public LogBlock logBlock()
    {
        checkState(logBlock != null);
        return logBlock;
    }

    @Override
    public int logSubBlockIndex() { return logMeta; }

    @Override
    public WoodBlock woodBlock()
    {
        checkState(woodBlock != null);
        return woodBlock;
    }

    @Override
    public int woodSubBlockIndex() { return planksMeta; }

    @Override
    public void assignWoodBlock(WoodBlock woodBlock)
    {
        checkState(this.woodBlock == null);
        this.woodBlock = woodBlock;
    }

    @Override
    public void assignWoodSubBlockIndex(int planksMeta) { this.planksMeta = planksMeta; }

    @Override
    public void assignStairsBlock(StairsBlock stairsBlock)
    {
        checkState(this.stairsBlock == null);
        this.stairsBlock = stairsBlock;
    }

    @Override
    public StairsBlock stairsBlock()
    {
        checkState(stairsBlock != null);
        return stairsBlock;
    }

    @Override
    public Block stairsModelBlock() { return woodBlock(); }

    @Override
    public int stairsModelSubBlockIndex() { return woodSubBlockIndex(); }

    @Override
    public String stairsName() { return speciesName(); }

    @Override
    public void assignSaplingBlock(SaplingBlock saplingBlock)
    {
        checkState(this.saplingBlock == null);
        this.saplingBlock = saplingBlock;
    }

    @Override
    public void assignSaplingSubBlockIndex(int saplingMeta) { this.saplingMeta = saplingMeta; }

    @Override
    public SaplingBlock saplingBlock()
    {
        checkState(saplingBlock != null);
        return saplingBlock;
    }

    @Override
    public int saplingSubBlockIndex() { return saplingMeta; }

    @Override
    @Deprecated
    public WorldGenerator treeGenerator() { return saplingTreeGen; }

    @Override
    public WorldGenerator saplingTreeGenerator() { return saplingTreeGen; }

    @Override
    public WorldGenerator worldTreeGenerator() { return worldTreeGen; }

    @Override
    public void assignDoubleSlabBlock(SlabBlock block)
    {
        checkState(doubleSlabBlock == null);
        doubleSlabBlock = block;
    }

    @Override
    public void assignSingleSlabBlock(SlabBlock block)
    {
        checkState(singleSlabBlock == null);
        singleSlabBlock = block;
    }

    @Override
    public void assignSlabSubBlockIndex(int slabMetadata) { this.slabMetadata = slabMetadata; }

    @Override
    public SlabBlock doubleSlabBlock()
    {
        checkState(doubleSlabBlock != null);
        return doubleSlabBlock;
    }

    @Override
    public SlabBlock singleSlabBlock()
    {
        checkState(singleSlabBlock != null);
        return singleSlabBlock;
    }

    @Override
    public int slabSubBlockIndex() { return slabMetadata; }

    @Override
    public Block slabModelBlock() { return woodBlock(); }

    @Override
    public int slabModelSubBlockIndex() { return woodSubBlockIndex(); }

    @Override
    public String slabName() { return speciesName(); }

    public enum Colorizer
    {
        ACEMUS_COLOR,
        BASIC_COLOR,
        CERASU_COLOR,
        KULIST_COLOR,
        NO_COLOR
    }
}
