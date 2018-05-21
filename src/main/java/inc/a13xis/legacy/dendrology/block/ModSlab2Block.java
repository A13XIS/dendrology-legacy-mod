package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public final class ModSlab2Block extends SlabBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumType.class);

	public ModSlab2Block(Iterable<? extends DefinesSlab> subBlocks) {
		super(ImmutableList.copyOf(subBlocks));
		setCreativeTab(TheMod.INSTANCE.creativeTab());
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.LATA));
	}

	@Override
	protected String resourcePrefix() {
		return TheMod.getResourcePrefix();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumType id1 = EnumType.fromId(meta);
		return meta < 8 ?
				getDefaultState().withProperty(VARIANT, EnumType.fromId(meta)).withProperty(HALF, EnumBlockHalf.BOTTOM) :
				getDefaultState().withProperty(VARIANT, EnumType.fromId(meta)).withProperty(HALF, EnumBlockHalf.TOP);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumType type = (EnumType) state.getValue(VARIANT);
		EnumBlockHalf half = (EnumBlockHalf) state.getValue(HALF);
		int id1 = type.getId();
		int id2 = type.getId() + 8;
		return half == null || half == EnumBlockHalf.BOTTOM ?
				type.getId() :
				type.getId() + 8;
	}

	@Override
	public int damageDropped(IBlockState state) {
		int id1 = getMetaFromState(state.withProperty(HALF, EnumBlockHalf.BOTTOM));
		return getMetaFromState(state.withProperty(HALF, EnumBlockHalf.BOTTOM));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{VARIANT, HALF});
	}

	@Override
	public IProperty getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable getTypeForItem(ItemStack stack) {
		if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof ModSlab2Block) {
			return getStateFromMeta(stack.getItemDamage()).getValue(VARIANT);
		} else
			return null;
	}

	public enum EnumType implements IStringSerializable {
		LATA("lata"),
		NUCIS("nucis"),
		PORFFOR("porffor"),
		SALYX("salyx"),
		TUOPA("tuopa");

		private final String species;

		EnumType(String name) {
			this.species = name;
		}

		public static EnumType fromId(int id) {
			if (id < 0 || id > 4) {
				return LATA;
			} else {
				return EnumType.values()[id];
			}
		}

		public String getName() {
			return species;
		}

		@Override
		public String toString() {
			return getName();
		}

		public int getId() {
			return ordinal();
		}
	}
}
