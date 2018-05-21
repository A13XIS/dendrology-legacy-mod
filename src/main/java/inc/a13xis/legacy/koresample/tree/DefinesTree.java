package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import net.minecraft.world.gen.feature.WorldGenerator;

@SuppressWarnings("InterfaceNeverImplemented")
public interface DefinesTree {
	LeavesBlock leavesBlock();

	Enum leavesSubBlockVariant();

	LogBlock logBlock();

	Enum logSubBlockVariant();

	SaplingBlock saplingBlock();

	Enum saplingSubBlockVariant();

	WorldGenerator saplingTreeGenerator();

	WorldGenerator worldTreeGenerator();
}
