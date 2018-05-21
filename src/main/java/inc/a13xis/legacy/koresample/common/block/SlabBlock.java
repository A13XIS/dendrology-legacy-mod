package inc.a13xis.legacy.koresample.common.block;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.koresample.common.util.slab.TheSingleSlabRegistry;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

@SuppressWarnings("AbstractClassNeverImplemented")
public abstract class SlabBlock extends BlockSlab {
	public static final int CAPACITY = 8;
	private static final int METADATA_MASK = CAPACITY - 1;
	private static final TheSingleSlabRegistry slabRegistry = TheSingleSlabRegistry.REFERENCE;
	private final ImmutableList<DefinesSlab> subBlocks;

	protected SlabBlock(Collection<? extends DefinesSlab> subBlocks) {
		super(Material.WOOD);

		checkArgument(!subBlocks.isEmpty());
		checkArgument(subBlocks.size() <= CAPACITY);
		this.subBlocks = ImmutableList.copyOf(subBlocks);
		setUnlocalizedName("slab");
	}

	private static int mask(int metadata) {
		return metadata & METADATA_MASK;
	}

	public static boolean isSingleSlab(Item item) {
		return slabRegistry.isSingleSlab(item);
	}

	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess access, BlockPos pos) {
		if (access == null || pos == null) {
			state.withProperty(HALF, EnumBlockHalf.BOTTOM);
		}
		return super.getActualState(state, access, pos);
	}

	@Override
	public final Item getItemDropped(IBlockState state, Random unused, int unused2) {
		final DefinesSlab subBlock = subBlocks.get(mask(this.getMetaFromState(state)));
		return Item.getItemFromBlock(subBlock.singleSlabBlock());
	}

	protected final ItemStack createStackedBlock(IBlockState state) {
		final DefinesSlab subBlock = subBlocks.get(mask(this.getMetaFromState(state)));
		return new ItemStack(Item.getItemFromBlock(subBlock.singleSlabBlock()), 2, subBlock.slabSubBlockVariant().ordinal());
	}

	@Override
	public final String getUnlocalizedName() {
		return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public final void getSubBlocks(CreativeTabs unused, NonNullList<ItemStack> subblocks) {
		for (int i = 0; i < subBlocks.size(); ++i) {
			//noinspection ObjectAllocationInLoop
			subblocks.add(new ItemStack(new ItemBlock(this), 1, i));
		}
	}

	public void registerItemModels() {
		for (DefinesSlab define : subBlocks()) {
			ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "half=bottom,variant=" + define.slabSubBlockVariant().name().toLowerCase());
			//ModelResourceLocation typeItemLocation = new ModelResourceLocation(getRegistryName().toString().substring(0,getRegistryName().toString().length()-1)+"_"+define.leavesSubBlockVariant().name().toLowerCase(),"inventory");
			Item blockItem = Item.getItemFromBlock(define.singleSlabBlock());
			ModelLoader.setCustomModelResourceLocation(blockItem, define.slabSubBlockVariant().ordinal(), typeLocation);
		}
	}

	@Override
	public final String getUnlocalizedName(int metadata) {
		int metadata1 = metadata;
		if (metadata1 < 0 || metadata1 >= subBlocks.size()) {
			metadata1 = 0;
		}

		return getUnlocalizedName() + '.' + subBlocks.get(metadata1).slabName();
	}

	@Override
	public boolean isDouble() {
		return !slabRegistry.isSingleSlab(this);
	}

	protected abstract String resourcePrefix();

	protected final List<DefinesSlab> subBlocks() {
		return Collections.unmodifiableList(subBlocks);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("subBlocks", subBlocks).toString();
	}
}
