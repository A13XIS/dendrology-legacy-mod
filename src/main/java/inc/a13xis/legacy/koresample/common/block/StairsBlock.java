package inc.a13xis.legacy.koresample.common.block;

import inc.a13xis.legacy.koresample.tree.DefinesStairs;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public abstract class StairsBlock extends BlockStairs {
	protected Enum variant;

	protected StairsBlock(DefinesStairs model) {
		super(model.stairsModelBlock().getStateFromMeta(model.stairsModelSubBlockVariant().ordinal()));
		this.variant = model.stairsModelSubBlockVariant();
		setUnlocalizedName("stairs");
	}

	@SuppressWarnings("WeakerAccess")
	protected static String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
	}

	@Override
	public final String getUnlocalizedName() {
		return "tile." + resourcePrefix() + getUnwrappedUnlocalizedName(super.getUnlocalizedName());
	}

	protected abstract String resourcePrefix();

	public void registerItemModels() {
		ModelResourceLocation typeLocation = new ModelResourceLocation(getRegistryName(), "facing=east,half=bottom,shape=straight");
		//ModelResourceLocation typeItemLocation = new ModelResourceLocation(getRegistryName().toString().substring(0,getRegistryName().toString().length()-1)+"_"+define.leavesSubBlockVariant().name().toLowerCase(),"inventory");
		Item blockItem = Item.getItemFromBlock(this);
		ModelLoader.setCustomModelResourceLocation(blockItem, 0, typeLocation);
	}
}
