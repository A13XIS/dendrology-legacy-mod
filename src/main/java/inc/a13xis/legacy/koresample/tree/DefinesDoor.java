package inc.a13xis.legacy.koresample.tree;

import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import net.minecraft.block.Block;

public interface DefinesDoor {
	void assignDoorBlock(DoorBlock block);

	DoorBlock doorBlock();

	Block doorModelBlock();

	Enum doorModelSubBlockVariant();

	String doorName();
}
