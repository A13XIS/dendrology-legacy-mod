package inc.a13xis.legacy.dendrology.content.overworld;

import inc.a13xis.legacy.dendrology.world.AcemusColorizer;
import inc.a13xis.legacy.dendrology.world.CerasuColorizer;
import inc.a13xis.legacy.dendrology.world.KulistColorizer;
import inc.a13xis.legacy.dendrology.world.gen.feature.*;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.*;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.google.common.base.Preconditions.checkState;
import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.Colorizer.*;

@SuppressWarnings({ "NonSerializableFieldInSerializableClass", "ClassHasNoToStringMethod" })
public enum OverworldTreeSpecies
        implements DefinesLeaves, DefinesLog, DefinesSapling, DefinesSlab, DefinesStairs, DefinesTree, DefinesWood, DefinesDoor
{
    // REORDERING WILL CAUSE DAMAGE TO SAVES
    ACEMUS(ACEMUS_COLOR, new AcemusTree(), new AcemusTree(false)),
    CEDRUM(NO_COLOR, new CedrumTree(), new CedrumTree(false)),
    CERASU(CERASU_COLOR, new CerasuTree(), new CerasuTree(false)),
    DELNAS(NO_COLOR, new DelnasTree(), new DelnasTree(false)),
    EWCALY(NO_COLOR, new EwcalyTree(), new EwcalyTree(false)),
    HEKUR(BASIC_COLOR, new HekurTree(), new HekurTree(false)),
    KIPARIS(NO_COLOR, new KiparisTree(), new KiparisTree(false)),
    KULIST(KULIST_COLOR, new KulistTree(), new KulistTree(false)),
    LATA(BASIC_COLOR, new LataTree(), new LataTree(false)),
    NUCIS(BASIC_COLOR, new NucisTree(), new NucisTree(false)),
    PORFFOR(NO_COLOR, new PorfforTree(), new PorfforTree(false)),
    SALYX(NO_COLOR, new SalyxTree(), new SalyxTree(false)),
    TUOPA(BASIC_COLOR, new TuopaTree(), new TuopaTree(false));

    private final AbstractTree saplingTreeGen;
    private final AbstractTree worldTreeGen;
    private final Colorizer colorizer;

    private Enum leavesVariant;
    private Enum logVariant;
    private Enum planksVariant;
    private Enum saplingVariant;
    private Enum slabVariant;

    private SlabBlock doubleSlabBlock = null;
    private LeavesBlock leavesBlock = null;
    private LogBlock logBlock = null;
    private WoodBlock woodBlock = null;
    private SaplingBlock saplingBlock = null;
    private SlabBlock singleSlabBlock = null;
    private StairsBlock stairsBlock = null;
    private DoorBlock doorBlock = null;

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
    }

    OverworldTreeSpecies(Colorizer colorizer, AbstractTree saplingTreeGen, AbstractTree worldTreeGen)
    {
        this(colorizer, saplingTreeGen, worldTreeGen, null);
    }


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
                return 0xffffff;

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
                return blockAccess.getBlockState(pos).getMapColor(blockAccess,pos).colorValue;

        }
    }

    @Override
    public void assignLeavesBlock(LeavesBlock leavesBlock)
    {
        checkState(this.leavesBlock == null);
        this.leavesBlock = leavesBlock;
    }

    @Override
    public void assignLeavesSubBlockVariant(Enum variant) { this.leavesVariant = variant; }

    @Override
    public LeavesBlock leavesBlock()
    {
        checkState(leavesBlock != null);
        return leavesBlock;
    }

    @Override
    public Enum leavesSubBlockVariant() { return leavesVariant; }

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
    public void assignLogSubBlockVariant(Enum variant) { this.logVariant = variant; }

    @Override
    public LogBlock logBlock()
    {
        checkState(logBlock != null);
        return logBlock;
    }

    @Override
    public Enum logSubBlockVariant() { return logVariant; }

    @Override
    public WoodBlock woodBlock()
    {
        checkState(woodBlock != null);
        return woodBlock;
    }

    @Override
    public Enum woodSubBlockVariant() { return planksVariant; }

    @Override
    public void assignWoodBlock(WoodBlock woodBlock)
    {
        checkState(this.woodBlock == null);
        this.woodBlock = woodBlock;
    }

    @Override
    public void assignWoodSubBlockVariant(Enum type) { this.planksVariant = type; }

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
    public Enum stairsModelSubBlockVariant() { return woodSubBlockVariant();
    }

    @Override
    public String stairsName() { return speciesName(); }

    @Override
    public void assignDoorBlock(DoorBlock doorBlock)
    {
        checkState(this.doorBlock == null);
        this.doorBlock = doorBlock;
    }

    @Override
    public DoorBlock doorBlock()
    {
        checkState(doorBlock != null);
        return doorBlock;
    }

    @Override
    public Block doorModelBlock() { return woodBlock(); }

    @Override
    public Enum doorModelSubBlockVariant() { return woodSubBlockVariant();
    }

    @Override
    public String doorName() { return speciesName(); }

    @Override
    public void assignSaplingBlock(SaplingBlock saplingBlock)
    {
        checkState(this.saplingBlock == null);
        this.saplingBlock = saplingBlock;
    }

    @Override
    public void assignSaplingSubBlockVariant(Enum type) { this.saplingVariant = type; }

    @Override
    public SaplingBlock saplingBlock()
    {
        checkState(saplingBlock != null);
        return saplingBlock;
    }

    @Override
    public Enum saplingSubBlockVariant() { return saplingVariant; }

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
    public void assignSlabSubBlockVariant(Enum slabMetadata) { this.slabVariant = slabMetadata; }

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
    public Enum slabSubBlockVariant() { return slabVariant; }

    @Override
    public Block slabModelBlock() { return woodBlock(); }

    @Override
    public Enum slabModelSubBlockVariant() { return woodSubBlockVariant(); }

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
