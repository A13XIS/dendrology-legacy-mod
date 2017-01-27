package inc.a13xis.legacy.dendrology.block;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public final class ModStairsBlock extends StairsBlock
{
    private ModWoodBlock.EnumType variant;
    public ModStairsBlock(DefinesStairs definition)
    {
        super(definition);
        variant=(ModWoodBlock.EnumType)definition.stairsModelSubBlockVariant();
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    protected String resourcePrefix()
    {
        return TheMod.getResourcePrefix();
    }

    @Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[] {FACING,HALF,SHAPE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
       return 0;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
