package inc.a13xis.legacy.koresample.common.util.slab;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import org.apache.commons.lang3.tuple.ImmutablePair;

public final class SingleDoubleSlab {
	private final ImmutablePair<SlabBlock, SlabBlock> pair;

	public SingleDoubleSlab(SlabBlock singleSlab, SlabBlock doubleSlab) {
		pair = ImmutablePair.of(singleSlab, doubleSlab);
	}

	public SlabBlock singleSlab() {
		return pair.left;
	}

	public SlabBlock doubleSlab() {
		return pair.right;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("pair", pair).toString();
	}
}
