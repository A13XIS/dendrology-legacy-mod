package inc.a13xis.legacy.dendrology.compat.chisel;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;


public final class ChiselWoodBlock extends Block
{
    private final String speciesName;
    private final String variationGroup;
    public final static PropertyEnum<VARIATIONS> VARIATION = PropertyEnum.create("variation",VARIATIONS.class);
    private ItemBlock blockItem;


    public ChiselWoodBlock(String variationGroup,String speciesName)
    {
        super(Material.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(ChiselWoodBlock.VARIATION,VARIATIONS.CLEAN));
        setHardness(2.0f);
        setResistance(5.0f);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
        this.speciesName = speciesName;
        this.variationGroup=variationGroup;
        setUnlocalizedName(variationGroup);
        setRegistryName(new ResourceLocation("chisel",variationGroup));
        blockItem = (ItemBlock)new ChiselWoodItem((Block)this,getSubBlockNames().toArray(new String[VARIATIONS.values().length])).setRegistryName(getRegistryName());
    }

    public final ImmutableList<String> getSubBlockNames()
    {
        final List<String> names = Lists.newArrayList();
        for (final VARIATIONS var : VARIATIONS.values())
            names.add(var.getName());
        return ImmutableList.copyOf(names);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }



    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(((PropertyEnum<VARIATIONS>)blockState.getProperty("variation"))).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(ChiselWoodBlock.VARIATION,VARIATIONS.byId(meta));
    }

    public ItemBlock registerAndNoteBlock()
    {
        GameRegistry.register(this);
        GameRegistry.register(blockItem);
        return blockItem;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { VARIATION });
    }

    protected static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public final void getSubBlocks(Item item, CreativeTabs unused, NonNullList<ItemStack> subblocks)
    {
        for (int i = 0; i < VARIATIONS.values().length; i++)
            subblocks.add(new ItemStack(item, 1, i));
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("variationGroup", variationGroup).add("variation",ChiselWoodBlock.VARIATION).toString();
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.chisel.%s", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    public IBlockState withVariation(VARIATIONS v) { return getDefaultState().withProperty(ChiselWoodBlock.VARIATION,v); }

    public String getSpeciesName() {
        return speciesName;
    }

    public enum VARIATIONS implements IStringSerializable{

            CLEAN("clean"),
            SHORT("short"),
            VERTICAL("vertical"),
            UNEVEN("uneven"),
            PARQUET("parquet"),
            FANCY("fancy"),
            BLINDS("blinds"),
            PANEL("panel"),
            DOUBLE("double"),
            CRATE("crate"),
            CRATEFANCY("cratefancy"),
            SCAFFOLD("scaffold"),
            LARGE("large"),
            CHAOTIC("chaotic"),
            VCHAOTIC("vchaotic");

            VARIATIONS(String name){
                this.name=name;
            }

            private String name;

            public String getName(){
                return name;
            }

            public static VARIATIONS byId(int id){
                for (VARIATIONS v:VARIATIONS.values()){
                    if(v.ordinal()==id){
                        return v;
                    }
                }
                return null;
            }
    }
}
