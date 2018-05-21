package inc.a13xis.legacy.koresample.tree.block;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("AbstractClassNeverImplemented")
public abstract class SaplingBlock extends BlockBush implements IGrowable {
	public static final int CAPACITY = 8;
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	private static final int METADATA_MASK = CAPACITY - 1;
	private final ImmutableList<DefinesSapling> subBlocks;

	protected SaplingBlock(Collection<? extends DefinesSapling> subBlocks) {
		checkArgument(!subBlocks.isEmpty());
		checkArgument(subBlocks.size() <= CAPACITY);
		this.subBlocks = ImmutableList.copyOf(subBlocks);

		setUnlocalizedName("sapling");
	}


	@SuppressWarnings("WeakerAccess")
	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	private static int mask(int metadata) {
		return metadata & METADATA_MASK;
	}

	protected final List<DefinesSapling> subBlocks() {
		return Collections.unmodifiableList(subBlocks);
	}

	public final List<String> subBlockNames() {
		final List<String> names = Lists.newArrayListWithCapacity(subBlocks.size());
		for (final DefinesSapling subBlock : subBlocks)
			names.add(subBlock.speciesName());
		return ImmutableList.copyOf(names);
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!TerrainGen.saplingGrowTree(world, rand, pos)) return;

		final int metadata = this.getMetaFromState(state.withProperty(STAGE, 0));
		final WorldGenerator treeGen = subBlocks.get(metadata).saplingTreeGenerator();
		world.setBlockToAir(pos);
		if (!treeGen.generate(world, rand, pos)) world.setBlockState(pos, this.getStateFromMeta(metadata), 4);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public final void getSubBlocks(CreativeTabs unused, NonNullList<ItemStack> subBlocks) {
		for (int i = 0; i < this.subBlocks.size(); i++)
			//noinspection ObjectAllocationInLoop
			subBlocks.add(new ItemStack(new ItemBlock(this), 1, i));
	}

	public void registerItemModels() {
		for (DefinesSapling define : subBlocks()) {
			ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "stage=0,variant=" + define.saplingSubBlockVariant().name().toLowerCase());
			//ModelResourceLocation typeItemLocation = new ModelResourceLocation(getRegistryName().toString().substring(0,getRegistryName().toString().length()-1)+"_"+define.leavesSubBlockVariant().name().toLowerCase(),"inventory");
			Item blockItem = Item.getItemFromBlock(define.saplingBlock());
			ModelLoader.setCustomModelResourceLocation(blockItem, define.saplingSubBlockVariant().ordinal(), typeLocation);
		}
	}

	@Override
	public final String getUnlocalizedName() {
		return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	protected abstract String resourcePrefix();

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("subBlocks", subBlocks).toString();
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if ((Integer) state.getValue(STAGE) == 0) {
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(worldIn, pos, state, rand);
		}
	}

	/**
	 * Check whether the given BlockPos has a Sapling of the given type
	 */
	public abstract boolean isTypeAt(World worldIn, BlockPos pos, Enum type);

	/**
	 * Get the damage value that this Block should drop
	 */
	public abstract int damageDropped(IBlockState state);

	/**
	 * Whether this IGrowable can grow
	 */
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state, rand);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public abstract IBlockState getStateFromMeta(int meta);

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public abstract int getMetaFromState(IBlockState state);

	protected abstract BlockStateContainer createBlockState();


}
