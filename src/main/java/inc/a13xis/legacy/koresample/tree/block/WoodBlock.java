package inc.a13xis.legacy.koresample.tree.block;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.koresample.tree.DefinesWood;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class WoodBlock extends Block {
	public static final int CAPACITY = 16;
	private final ImmutableList<DefinesWood> subBlocks;

	protected WoodBlock(Collection<? extends DefinesWood> subBlocks) {
		super(Material.WOOD);
		Preconditions.checkArgument(!subBlocks.isEmpty());
		Preconditions.checkArgument(subBlocks.size() <= CAPACITY);
		this.subBlocks = ImmutableList.copyOf(subBlocks);
		this.setUnlocalizedName("planks");
	}

	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	public static String getRawUnlocalizedName(WoodBlock wood) {
		String unwrapped = getUnwrappedUnlocalizedName(wood.getUnlocalizedName());
		return unwrapped.substring(unwrapped.indexOf(":"));
	}

	protected final List<DefinesWood> subBlocks() {
		return Collections.unmodifiableList(subBlocks);
	}

	public final ImmutableList<String> getSubBlockNames() {
		final List<String> names = Lists.newArrayList();
		for (final DefinesWood subBlock : subBlocks)
			names.add(subBlock.speciesName());
		return ImmutableList.copyOf(names);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public final void getSubBlocks(CreativeTabs unused, NonNullList<ItemStack> subblocks) {
		for (int i = 0; i < subBlocks.size(); i++)
			//noinspection ObjectAllocationInLoop
			subblocks.add(new ItemStack(new ItemBlock(this), 1, i));
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModels() {
		for (DefinesWood define : subBlocks()) {
			ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "variant=" + define.woodSubBlockVariant().name().toLowerCase());
			Item blockItem = Item.getItemFromBlock(define.woodBlock());
			ModelLoader.setCustomModelResourceLocation(blockItem, define.woodSubBlockVariant().ordinal(), typeLocation);

		}
	}

	protected abstract String resourcePrefix();

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("subBlocks", subBlocks).toString();
	}
}
