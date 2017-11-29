package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public final class ModLog4Block extends LogBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumType.class);
    public ModLog4Block(Iterable<? extends DefinesLog> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.TUOPA));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        switch(meta/4){
            case 0:
                state = state.withProperty(LOG_AXIS,EnumAxis.Y);
                break;
            case 1:
                state = state.withProperty(LOG_AXIS,EnumAxis.X);
                break;
            case 2:
                state = state.withProperty(LOG_AXIS,EnumAxis.Z);
                break;
            case 3:
                state = state.withProperty(LOG_AXIS,EnumAxis.NONE);
        }
        return state.withProperty(VARIANT,EnumType.TUOPA);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int par = state.getValue(LOG_AXIS).equals(EnumAxis.Y)?0:state.getValue(LOG_AXIS).equals(EnumAxis.X)?1:state.getValue(LOG_AXIS).equals(EnumAxis.Z)?2:3;
        return par*4;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state.withProperty(LOG_AXIS,EnumAxis.Y));
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{VARIANT,LOG_AXIS});
    }

    public enum EnumType implements IStringSerializable {
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

        public static EnumType getTuopa() {
             return TUOPA;
        }
    }
}
