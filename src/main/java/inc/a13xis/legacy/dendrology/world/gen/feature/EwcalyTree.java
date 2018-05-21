package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.dendrology.world.gen.feature.ewcaly.LargeEwcalyTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.ewcaly.NormalEwcalyTree;
import inc.a13xis.legacy.koresample.tree.DefinesTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EwcalyTree extends AbstractTree {
	private final AbstractTree treeGen;
	private final AbstractTree largeTreeGen;

	public EwcalyTree(boolean fromSapling) {
		super(fromSapling);
		treeGen = new NormalEwcalyTree(fromSapling);
		largeTreeGen = new LargeEwcalyTree(fromSapling);
	}

	public EwcalyTree() {
		this(true);
	}

	@Override
	public void setTree(DefinesTree tree) {
		treeGen.setTree(tree);
		largeTreeGen.setTree(tree);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("treeGen", treeGen).add("largeTreeGen", largeTreeGen).toString();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		if (rand.nextInt(7) > 1)
			return treeGen.generate(world, rand, pos);

		return largeTreeGen.generate(world, rand, pos);
	}
}
