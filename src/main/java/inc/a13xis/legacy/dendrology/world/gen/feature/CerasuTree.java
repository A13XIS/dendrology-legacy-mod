package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.dendrology.world.gen.feature.cerasu.LargeCerasuTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.vanilla.VanillaTree;
import inc.a13xis.legacy.koresample.tree.DefinesTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CerasuTree extends AbstractTree {
	private final AbstractTree treeGen;
	private final AbstractTree largeTreeGen;

	public CerasuTree(boolean fromSapling) {
		super(fromSapling);
		treeGen = new VanillaTree(fromSapling);
		largeTreeGen = new LargeCerasuTree(fromSapling);
	}

	public CerasuTree() {
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
		if (rand.nextInt(10) < 9) {
			return treeGen.generate(world, rand, pos);
		}

		return largeTreeGen.generate(world, rand, pos);
	}
}
