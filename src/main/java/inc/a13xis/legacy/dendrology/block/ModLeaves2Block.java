
package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import net.minecraft.block.BlockPlanks;
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

public final class ModLeaves2Block extends LeavesBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModLog2Block.EnumType.class);
    public ModLeaves2Block(Iterable<? extends DefinesLeaves> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModLog2Block.EnumType.EWCALY).withProperty(CHECK_DECAY, Boolean.TRUE).withProperty(DECAYABLE, Boolean.TRUE));
    }

    @Override
    public int quantityDropped(Random random)
    {
        final int rarity = Settings.INSTANCE.saplingDropRarity();
        return rarity == 0 || random.nextInt(rarity) != 0 ? 0 : 1;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[]{VARIANT,CHECK_DECAY,DECAYABLE});
    }

    @Override
    public ModLog2Block.EnumType getModWoodType(int meta) {
        return ModLog2Block.EnumType.fromId(meta);
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.byMetadata((4+meta)%6);
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
        state = state.withProperty(VARIANT,ModLog2Block.EnumType.fromId(meta%4));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        ModLog2Block.EnumType type = (ModLog2Block.EnumType) state.getValue(ModLog2Block.VARIANT);
        boolean check = (Boolean) state.getValue(CHECK_DECAY);
        boolean dcable = (Boolean) state.getValue(CHECK_DECAY);
        int par = check?dcable?0:1:dcable?2:3;
        return par*4+type.ordinal();
    }

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        return Settings.INSTANCE.saplingDropRarity();
    }

}
