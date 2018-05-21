package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.ModWoodBlock;
import inc.a13xis.legacy.koresample.tree.item.WoodItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ModWoodItem extends WoodItem {
	public ModWoodItem(Block block, ModWoodBlock log, String[] names) {
		super(block, log, names);
		this.setHasSubtypes(true);
	}

	@Override
	public final String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ModWoodBlock.EnumType.fromId(stack.getItemDamage());
	}

}
