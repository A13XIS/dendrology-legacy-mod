package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.content.ProvidesPotionEffect;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public final class ModSaplingBlock extends SaplingBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);
    public ModSaplingBlock(Iterable<? extends DefinesSapling> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModWoodBlock.EnumType.ACEMUS).withProperty(STAGE, 0) );
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public boolean isTypeAt(World worldIn, BlockPos pos, Enum type) {
        return type instanceof ModWoodBlock.EnumType && type.equals(worldIn.getBlockState(pos).getValue(VARIANT));
    }

    @SuppressWarnings("ReturnOfNull")
    public String getPotionEffect(ItemStack itemStack)
    {
        final List<DefinesSapling> subBlocks = subBlocks();
        final int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= subBlocks.size()) return null;

        final DefinesSapling subBlock = subBlocks.get(itemDamage);
        return subBlock instanceof ProvidesPotionEffect ? ((ProvidesPotionEffect) subBlock).potionEffect() : null;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        ModWoodBlock.EnumType id=ModWoodBlock.EnumType.fromId(meta);
        return getDefaultState().withProperty(VARIANT,ModWoodBlock.EnumType.fromId(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModWoodBlock.EnumType type = (ModWoodBlock.EnumType) state.getValue(ModWoodBlock.VARIANT);
        int id = type.ordinal();
        return type.ordinal();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[] {VARIANT, STAGE});
    }
}
