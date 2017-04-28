package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.item.ModWoodItem;
import inc.a13xis.legacy.koresample.tree.DefinesWood;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Collection;


public final class ModWoodBlock extends WoodBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);

    protected static String getUnwrappedUnprefixedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(':') + 1);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnprefixedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { ModWoodBlock.VARIANT });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT,ModWoodBlock.EnumType.fromId(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumType type = (EnumType) state.getValue(ModWoodBlock.VARIANT);
        return type.ordinal();
    }

    public ModWoodBlock(Iterable<? extends DefinesWood> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0f);
        setResistance(5.0f);
        setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ModWoodBlock.VARIANT, EnumType.ACEMUS));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }


    public IBlockState getBlockState(int id){
       return ModWoodBlock.getStateById(id);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    public enum EnumType implements IStringSerializable{
        ACEMUS("acemus"),
        CEDRUM("cedrum"),
        CERASU("cerasu"),
        DELNAS("delnas"),
        EWCALY("ewcaly"),
        HEKUR("hekur"),
        KIPARIS("kiparis"),
        KULIST("kulist"),
        LATA("lata"),
        NUCIS("nucis"),
        PORFFOR("porffor"),
        SALYX("salyx"),
        TUOPA("tuopa");

        private final String species;

        EnumType(String name){
            this.species=name;
        }

        public String getName(){
            return species;
        }

        @Override
        public String toString() {
            return getName();
        }

        public static EnumType fromId(int id) {
            if(id<0||id>12){
                return ACEMUS;
            }
            else{
                return EnumType.values()[id];
            }
        }

        public int getId() {
            return ordinal();
        }
    }

}
