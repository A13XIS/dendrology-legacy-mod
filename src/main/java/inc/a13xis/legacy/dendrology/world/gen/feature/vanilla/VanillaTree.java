package inc.a13xis.legacy.dendrology.world.gen.feature.vanilla;

import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class VanillaTree extends AbstractTree {
	public VanillaTree(boolean fromSapling) {
		super(fromSapling);
	}

	@Override
	protected boolean hasRoomToGrow(World world, BlockPos pos, int height) {
		for (BlockPos pos1 = pos; pos1.getY() <= pos.up(1 + height).getY(); pos1 = pos1.up()) {
			//noinspection NestedConditionalExpression
			final int radius = pos1.getY() >= pos.getY() + 1 + height - 2 ? 2 : pos1.getY() == pos.getY() ? 0 : 1;
			for (pos1 = pos1.east(radius - pos.getX() - pos1.getX()); pos1.getX() <= pos.east(radius).getX(); pos1 = pos1.east())
				for (pos1 = pos1.south(radius - pos.getZ() - pos1.getZ()); pos1.getZ() <= pos.south(radius).getZ(); pos1 = pos1.south())
					if (pos1.getY() >= 0 && pos1.getY() < 256) {
						if (!isReplaceable(world, pos1)) return false;
					} else return false;
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		final int height = 4 + rand.nextInt(3) + rand.nextInt(7);

		if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

		final Block block = world.getBlockState(pos.down()).getBlock();
		block.onPlantGrow(world.getBlockState(pos.down()), world, pos.down(), pos);

		placeCanopy(world, rand, pos, height);

		for (int dY = 0; dY < height; ++dY)
			placeLog(world, pos.up(dY));

		return true;
	}

	private void placeCanopy(World world, Random rand, BlockPos pos, int height) {
		for (BlockPos pos1 = pos.up(height - 3); pos1.getY() <= pos.up(height).getY(); pos1 = pos1.up()) {
			final int distanceToTopOfTrunk = pos1.getY() - (pos.up(height).getY());
			final int radius = 1 - distanceToTopOfTrunk / 2;

			for (pos1 = new BlockPos(pos.getX() - radius, pos1.getY(), pos1.getZ()); pos1.getX() <= pos.east(radius).getX(); pos1 = pos1.east()) {
				final int dX = pos1.getX() - pos.getX();

				for (pos1 = new BlockPos(pos1.getX(), pos1.getY(), pos.getZ() - radius); pos1.getZ() <= pos.south(radius).getZ(); pos1 = pos1.south()) {
					final int dZ = pos1.getZ() - pos.getZ();

					if (Math.abs(dX) != radius || Math.abs(dZ) != radius || rand.nextInt(2) != 0 && distanceToTopOfTrunk != 0) {
						if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1)
							placeLeaves(world, pos1, true);
						else {
							placeLeaves(world, pos1);
						}
					}
				}
			}
		}
	}
}
