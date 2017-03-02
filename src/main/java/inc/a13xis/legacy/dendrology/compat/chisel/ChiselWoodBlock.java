package inc.a13xis.legacy.dendrology.compat.chisel;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModWoodBlock;
import inc.a13xis.legacy.koresample.common.util.multiblock.SubBlockManager;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import java.util.List;

public final class ChiselWoodBlock extends Block
{
    public static final PropertyEnum VARIATIONS = PropertyEnum.create("variation", EnumVars.class);

    private final SubBlockManager subBlocks;
    private final String speciesName;

    public ChiselWoodBlock(String speciesName,SubBlockManager subBlocks)
    {
        super(Material.WOOD);
        this.subBlocks = subBlocks;
        setHardness(2.0f);
        setResistance(5.0f);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
        setUnlocalizedName("chisel."+speciesName+"_planks");

        this.speciesName = speciesName;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { VARIATIONS });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIATIONS,EnumVars.fromId(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumVars var = (EnumVars) state.getValue(VARIATIONS);
        return var.ordinal();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
        subBlocks.getSubBlocks(item, tabs, list);
    }

    public String getVariationName(int i)
    {
        return TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,String.format("chisel.%s:planks.%s.%s.name", TheMod.MOD_ID, speciesName, EnumVars.values()[i])):net.minecraft.client.resources.I18n.format("chisel.%s:planks.%s.%s.name", TheMod.MOD_ID, speciesName, EnumVars.values()[i]);
    }

    public String getSpecies() {
        return speciesName;
    }

    public  enum EnumVars implements IStringSerializable{
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
        VERTICALCHAOTIC("verticalchaotic");

        String representation;

        EnumVars(String name){
            this.representation=name;
        }

        public String getName(){
            return representation;
        }

        @Override
        public String toString() {
            return getName();
        }

        public static EnumVars fromId(int id) {
            if(id<0||id>12){
                return CLEAN;
            }
            else{
                return values()[id];
            }
        }
    }

    public ChiselWoodBlock cloneIt() throws CloneNotSupportedException {
        return (ChiselWoodBlock) this.clone();
    }
}
