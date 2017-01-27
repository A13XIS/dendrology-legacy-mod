package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

public final class ModLogBlock extends LogBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);
    public ModLogBlock(Iterable<? extends DefinesLog> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModWoodBlock.EnumType.ACEMUS));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT,ModWoodBlock.EnumType.fromId(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModWoodBlock.EnumType type = (ModWoodBlock.EnumType) state.getValue(ModWoodBlock.VARIANT);
        return type.ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[]{VARIANT,LOG_AXIS});
    }
}
