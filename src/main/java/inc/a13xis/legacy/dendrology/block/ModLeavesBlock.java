package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ModLeavesBlock extends LeavesBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModLogBlock.EnumType.class);

    public ModLeavesBlock(Iterable<? extends DefinesLeaves> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModLogBlock.EnumType.ACEMUS).withProperty(CHECK_DECAY, Boolean.TRUE).withProperty(DECAYABLE, Boolean.TRUE));
    }

    @Override
    public int quantityDropped(Random random)
    {
        final int rarity = Settings.INSTANCE.saplingDropRarity();
        return rarity == 0 || random.nextInt(rarity) != 0 ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        BlockStateContainer bs = new BlockStateContainer(this, new IProperty[]{VARIANT,CHECK_DECAY,DECAYABLE});

        return bs;
    }

    @Override
    public ModLogBlock.EnumType getWoodType(int meta) {
       return ModLogBlock.EnumType.fromId(meta);
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
        IBlockState state = getDefaultState();
        switch(meta/4){
            case 0:
                state = state.withProperty(CHECK_DECAY,true).withProperty(DECAYABLE,true);
            break;
            case 1:
                state = state.withProperty(CHECK_DECAY,true).withProperty(DECAYABLE,false);
            break;
            case 2:
                state = state.withProperty(CHECK_DECAY,false).withProperty(DECAYABLE,true);
            break;
            case 3:
                state = state.withProperty(CHECK_DECAY,false).withProperty(DECAYABLE,false);
            break;
        }
        state = state.withProperty(VARIANT,ModLogBlock.EnumType.fromId(meta%4));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModLogBlock.EnumType type = (ModLogBlock.EnumType) state.getValue(ModLogBlock.VARIANT);
        boolean check = (Boolean) state.getValue(CHECK_DECAY);
        boolean dcable = (Boolean) state.getValue(CHECK_DECAY);
        int par = check?dcable?0:1:dcable?2:3;
        return par*4+type.ordinal();
    }

    protected boolean needMask(){
        return false;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state.withProperty(CHECK_DECAY,true).withProperty(DECAYABLE,true));
    }

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        return Settings.INSTANCE.saplingDropRarity();
    }


}
