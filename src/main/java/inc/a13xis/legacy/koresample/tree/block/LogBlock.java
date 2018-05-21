package inc.a13xis.legacy.koresample.tree.block;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import inc.a13xis.legacy.koresample.tree.DefinesLog;
import net.minecraft.block.BlockLog;
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

import static com.google.common.base.Preconditions.checkArgument;

public abstract class LogBlock extends BlockLog {
	public static final int CAPACITY = 4;
	private final ImmutableList<DefinesLog> subBlocks;

	protected LogBlock(Collection<? extends DefinesLog> subBlocks) {
		checkArgument(!subBlocks.isEmpty());
		checkArgument(subBlocks.size() <= CAPACITY);
		this.subBlocks = ImmutableList.copyOf(subBlocks);
		setUnlocalizedName("log");
	}

	@SuppressWarnings("WeakerAccess")
	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	protected final List<DefinesLog> subBlocks() {
		return Collections.unmodifiableList(subBlocks);
	}

	public final ImmutableList<String> getSubBlockNames() {
		final List<String> names = Lists.newArrayList();
		for (final DefinesLog subBlock : subBlocks)
			names.add(subBlock.speciesName());
		return ImmutableList.copyOf(names);
	}

	@Override
	public final String getUnlocalizedName() {
		return String.format("tile.%s%s", resourcePrefix(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(CreativeTabs unused, NonNullList<ItemStack> items) {
		for (int i = 0; i < subBlocks.size(); i++)
			items.add(new ItemStack(new ItemBlock(this), 1, i));
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModels() {
		for (DefinesLog define : subBlocks()) {
			ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "axis=y,variant=" + define.logSubBlockVariant().name().toLowerCase());
			//ModelResourceLocation typeItemLocation = new ModelResourceLocation(getRegistryName().toString().substring(0,getRegistryName().toString().length()-1)+"_"+define.leavesSubBlockVariant().name().toLowerCase(),"inventory");
			Item blockItem = Item.getItemFromBlock(define.logBlock());
			ModelLoader.setCustomModelResourceLocation(blockItem, define.logSubBlockVariant().ordinal(), typeLocation);
		}
	}

	protected abstract String resourcePrefix();

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("subBlocks", subBlocks).toString();
	}
}
