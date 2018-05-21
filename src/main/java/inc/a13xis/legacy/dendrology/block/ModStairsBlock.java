package inc.a13xis.legacy.dendrology.block;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public final class ModStairsBlock extends StairsBlock {
	public ModStairsBlock(DefinesStairs definition) {
		super(definition);
		setCreativeTab(TheMod.INSTANCE.creativeTab());
		setRegistryName("staris_" + variant.name().toLowerCase());
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST).withProperty(HALF, EnumHalf.BOTTOM).withProperty(SHAPE, EnumShape.STRAIGHT));
	}

	@Override
	protected String resourcePrefix() {
		return TheMod.getResourcePrefix();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		BlockStateContainer bs = new BlockStateContainer(this, new IProperty[]{FACING, HALF, SHAPE});
		return bs;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		if ((meta / 2) == 0) {
			state = state.withProperty(HALF, EnumHalf.BOTTOM);
		} else {
			state = state.withProperty(HALF, EnumHalf.TOP);
		}
		switch (meta % 4) {
			case 0:
				state = state.withProperty(FACING, EnumFacing.EAST);
				break;
			case 1:
				state = state.withProperty(FACING, EnumFacing.WEST);
				break;
			case 2:
				state = state.withProperty(FACING, EnumFacing.SOUTH);
				break;
			case 3:
				state = state.withProperty(FACING, EnumFacing.NORTH);
				break;
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int par = state.getValue(HALF).equals(EnumHalf.BOTTOM) ? 0 : 4;
		int meta;
		switch ((EnumFacing) state.getValue(FACING)) {
			case NORTH:
				meta = 3;
				break;
			case SOUTH:
				meta = 2;
				break;
			case WEST:
				meta = 1;
				break;
			case EAST:
				meta = 0;
				break;
			default:
				return -1;
		}
		return par + meta;
	}


	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}
}
