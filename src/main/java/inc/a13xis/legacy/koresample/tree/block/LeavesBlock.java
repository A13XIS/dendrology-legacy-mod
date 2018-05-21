package inc.a13xis.legacy.koresample.tree.block;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class LeavesBlock extends BlockLeaves {
	public static final int CAPACITY = 4;
	private static final int METADATA_MASK = CAPACITY - 1;
	private final ImmutableList<DefinesLeaves> subBlocks;
	int[] surroundings;

	protected LeavesBlock(Collection<? extends DefinesLeaves> subBlocks) {
		super();
		checkArgument(!subBlocks.isEmpty());
		checkArgument(subBlocks.size() <= CAPACITY);
		this.subBlocks = ImmutableList.copyOf(subBlocks);
		this.setTickRandomly(true);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
		setUnlocalizedName("leaves");
	}

	private static int mask(int metadata) {
		return metadata & METADATA_MASK;
	}

	@SuppressWarnings("WeakerAccess")
	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	public static String getRawUnlocalizedName(LeavesBlock leaves) {
		String unwrapped = getUnwrappedUnlocalizedName(leaves.getUnlocalizedName());
		return unwrapped.substring(unwrapped.indexOf(":") + 1);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}

	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	protected final List<DefinesLeaves> subBlocks() {
		return Collections.unmodifiableList(subBlocks);
	}

	@Override
	public final Item getItemDropped(IBlockState state, Random unused, int unused2) {
		return Item.getItemFromBlock(subBlocks.get(mask(this.getMetaFromState(state))).saplingDefinition().saplingBlock());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return subBlocks.get(mask(this.getMetaFromState(state))).leavesSubBlockVariant().ordinal();
	}

	public final String[] getSpeciesNames() //func_150125_e
	{
		final List<String> names = Lists.newArrayList();
		for (final DefinesLeaves subBlock : subBlocks)
			names.add(subBlock.speciesName());
		return names.toArray(new String[names.size()]);
	}

	public final String getUnlocalizedName() {
		return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Deprecated
	public final int getDamageValue(World world, BlockPos pos) {
		return this.getMetaFromState(world.getBlockState(pos)) & 3;
	}

	public final void getSubBlocks(Item item, CreativeTabs unused, List subBlocks) {
		for (int i = 0; i < this.subBlocks.size(); i++)
			//noinspection ObjectAllocationInLoop
			subBlocks.add(new ItemStack(item, 1, i));
	}

	public void registerItemModels() {
		for (DefinesLeaves define : subBlocks()) {
			ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "check_decay=true,decayable=true,variant=" + define.leavesSubBlockVariant().name().toLowerCase());
			//ModelResourceLocation typeItemLocation = new ModelResourceLocation(getRegistryName().toString().substring(0,getRegistryName().toString().length()-1)+"_"+define.leavesSubBlockVariant().name().toLowerCase(),"inventory");
			Item blockItem = Item.getItemFromBlock(define.leavesBlock());
			ModelLoader.setCustomModelResourceLocation(blockItem, define.leavesSubBlockVariant().ordinal(), typeLocation);
		}
	}

	protected abstract String resourcePrefix();

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("subBlocks", subBlocks).toString();
	}

	public abstract BlockPlanks.EnumType getWoodType(int meta);

	public abstract Enum getModWoodType(int meta);

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue() && ((Boolean) state.getValue(DECAYABLE)).booleanValue()) {
				int i = 4;
				int j = 5;
				int k = pos.getX();
				int l = pos.getY();
				int i1 = pos.getZ();
				int j1 = 32;
				int k1 = 1024;
				int l1 = 16;

				if (this.surroundings == null) {
					this.surroundings = new int[32768];
				}

				if (worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5))) {
					BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

					for (int i2 = -4; i2 <= 4; ++i2) {
						for (int j2 = -4; j2 <= 4; ++j2) {
							for (int k2 = -4; k2 <= 4; ++k2) {
								IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
								Block block = iblockstate.getBlock();

								if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
									if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
										this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
									} else {
										this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
									}
								} else {
									this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
								}
							}
						}
					}

					for (int i3 = 1; i3 <= 4; ++i3) {
						for (int j3 = -4; j3 <= 4; ++j3) {
							for (int k3 = -4; k3 <= 4; ++k3) {
								for (int l3 = -4; l3 <= 4; ++l3) {
									if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1) {
										if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
											this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
										}

										if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2) {
											this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
										}

										if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2) {
											this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
										}

										if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2) {
											this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
										}

										if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2) {
											this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
										}

										if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2) {
											this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
										}
									}
								}
							}
						}
					}
				}

				int l2 = this.surroundings[16912];

				if (l2 >= 0) {

				} else {
					this.destroy(worldIn, pos);
				}
			}
		}
	}

	private void destroy(World worldIn, BlockPos pos) {
		this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
		worldIn.setBlockToAir(pos);
	}

    /*@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        return Colorizer.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
    }*/
}
