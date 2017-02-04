package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public final class ModSlabBlock extends SlabBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumType.class);
    public ModSlabBlock(Iterable<? extends DefinesSlab> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.ACEMUS));
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumType id1 = EnumType.fromId(meta);
        return meta<8?
                getDefaultState().withProperty(VARIANT, EnumType.fromId(meta)).withProperty(HALF,EnumBlockHalf.BOTTOM):
                getDefaultState().withProperty(VARIANT, EnumType.fromId(meta)).withProperty(HALF,EnumBlockHalf.TOP);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumType type = (EnumType) state.getValue(VARIANT);
        EnumBlockHalf half = (EnumBlockHalf)state.getValue(HALF);
        int id1 = type.getId();
        int id2 = type.getId()+8;
        return half==null||half==EnumBlockHalf.BOTTOM?
                type.getId():
                type.getId()+8;
    }

    @Override
    public int damageDropped(IBlockState state) {
        int id1 = getMetaFromState(state.withProperty(HALF,EnumBlockHalf.BOTTOM));
        return getMetaFromState(state.withProperty(HALF,EnumBlockHalf.BOTTOM));
    }

    @Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[] { VARIANT , HALF});
    }

    @Override
    public IProperty getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Object getVariant(ItemStack stack) {
        if(stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof ModSlabBlock){
            return getStateFromMeta(stack.getItemDamage()).getValue(VARIANT);
        }
        else
        return null;
    }

    public enum EnumType implements IStringSerializable {
        ACEMUS("acemus"),
        CEDRUM("cedrum"),
        CERASU("cerasu"),
        DELNAS("delnas"),
        EWCALY("ewcaly"),
        HEKUR("hekur"),
        KIPARIS("kiparis"),
        KULIST("kulist");

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
            if(id<0||id>7){
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
