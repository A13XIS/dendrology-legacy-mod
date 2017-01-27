package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ModLeavesBlock extends LeavesBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);
    public ModLeavesBlock(Iterable<? extends DefinesLeaves> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModWoodBlock.EnumType.ACEMUS).withProperty(CHECK_DECAY, Boolean.TRUE).withProperty(DECAYABLE, Boolean.TRUE));
    }

    @Override
    public int quantityDropped(Random random)
    {
        final int rarity = Settings.INSTANCE.saplingDropRarity();
        return rarity == 0 || random.nextInt(rarity) != 0 ? 0 : 1;
    }

    @Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[]{VARIANT,CHECK_DECAY,DECAYABLE});
    }

    @Override
    public ModWoodBlock.EnumType getWoodType(int meta) {
       return meta<=0?ModWoodBlock.EnumType.fromId(0,meta): ModWoodBlock.EnumType.fromId(1,meta-8);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList<ItemStack> list=new ArrayList<ItemStack>();
        list.add(new ItemStack(Item.getItemFromBlock(this),1,getMetaFromState(world.getBlockState(pos))));
        return list;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta<8?getDefaultState().withProperty(ModWoodBlock.VARIANT, ModWoodBlock.EnumType.fromId(0,meta)):getDefaultState().withProperty(ModWoodBlock.VARIANT, ModWoodBlock.EnumType.fromId(1,meta-8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModWoodBlock.EnumType type = (ModWoodBlock.EnumType) state.getValue(ModWoodBlock.VARIANT);
        return type.getId();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

}
