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

public final class ModSlabBlock extends SlabBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);
    public ModSlabBlock(Iterable<? extends DefinesSlab> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModWoodBlock.EnumType.ACEMUS));
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, ModWoodBlock.EnumType.fromId(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModWoodBlock.EnumType type = (ModWoodBlock.EnumType) state.getValue(VARIANT);
        return type.ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
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
            return getStateFromMeta(stack.getItem().getDamage(stack)).getValue(VARIANT);
        }
        return null;
    }


}
