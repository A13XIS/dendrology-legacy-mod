package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.block.ModSlab2Block;
import inc.a13xis.legacy.dendrology.block.ModSlabBlock;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.tree.item.SlabItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ModSlabItem extends SlabItem {
	SlabBlock sslabBlock;

	public ModSlabItem(Block block, ModSlabBlock singleSlab, ModSlabBlock doubleSlab) {
		super(block, singleSlab, doubleSlab);
		sslabBlock = singleSlab;
		//dslabBlock=doubleSlab;
	}

	public ModSlabItem(Block block, ModSlab2Block singleSlab, ModSlab2Block doubleSlab) {
		super(block, singleSlab, doubleSlab);
		sslabBlock = singleSlab;
		//dslabBlock=doubleSlab;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum variant = sslabBlock instanceof ModSlabBlock ? (Enum) sslabBlock.getStateFromMeta(stack.getItemDamage()).getValue(ModSlabBlock.VARIANT)
				: sslabBlock instanceof ModSlab2Block ? (Enum) sslabBlock.getStateFromMeta(stack.getItemDamage()).getValue(ModSlab2Block.VARIANT)
				: null;
		return super.getUnlocalizedName() + "." + variant.name().toLowerCase();
	}
}
