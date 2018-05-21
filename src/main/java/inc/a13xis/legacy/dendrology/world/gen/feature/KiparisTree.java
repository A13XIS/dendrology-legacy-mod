package inc.a13xis.legacy.dendrology.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class KiparisTree extends AbstractTree {
	public KiparisTree(boolean fromSapling) {
		super(fromSapling);
	}

	public KiparisTree() {
		this(true);
	}

	@Override
	protected boolean canBeReplacedByLog(World world, BlockPos pos) {
		return super.canBeReplacedByLog(world, pos) || world.getBlockState(pos).getMaterial().equals(Material.WATER);
	}

	@Override
	public boolean isReplaceable(World world, BlockPos pos) {
		return super.isReplaceable(world, pos) || world.getBlockState(pos).getMaterial().equals(Material.WATER);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		final Random rng = new Random();
		rng.setSeed(rand.nextLong());

		final int size =
				1 + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(2) == 0 ? 1 : 0);
		final int height = 4 * size + 1;

		if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

		final Block block = world.getBlockState(pos.down()).getBlock();
		block.onPlantGrow(world.getBlockState(pos.down()), world, pos.down(), pos);

		for (int dY = 0; dY <= height; dY++) {
			if (dY != height) placeLog(world, pos.up(dY));

			if (dY >= 1) {
				switch (size) {
					case 1:
						genSmallLeaves(world, pos.up(dY));
						break;
					case 2:
						genMediumLeaves(world, pos, dY);
						break;
					case 3:
						genLargeLeaves(world, pos, dY);
						break;
					default:
						genExtraLargeLeaves(world, pos, dY);
						break;
				}
			}

			if (dY == height) placeLeaves(world, pos.up(dY + 1), true);
			if (dY == height && (size == 4 || size == 3)) placeLeaves(world, pos.up(dY + 2));
		}
		return true;
	}

	private void genExtraLargeLeaves(World world, BlockPos pos, int dY) {
		for (int dX = -3; dX <= 3; dX++)
			for (int dZ = -3; dZ <= 3; dZ++) {
				if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);

				if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY <= 14 && dY >= 2)
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);

				if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) && dY == 12 ||
						dY == 11 || dY == 3)
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);

				if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
						(Math.abs(dX) != 2 || Math.abs(dZ) != 3) && dY <= 10 && dY >= 4)
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
			}
	}

	private void genLargeLeaves(World world, BlockPos pos, int dY) {
		for (int dX = -2; dX <= 2; dX++)
			for (int dZ = -2; dZ <= 2; dZ++) {
				if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);

				if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
						(Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 10 && dY >= 2)
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
			}
	}

	private void genMediumLeaves(World world, BlockPos pos, int dY) {
		for (int dX = -2; dX <= 2; dX++)
			for (int dZ = -2; dZ <= 2; dZ++) {
				if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1)) {
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
				}
				if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && dY == 7) {
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
				}
				if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
						(Math.abs(dX) != 1 || Math.abs(dZ) != 2) && dY <= 6 && dY >= 2) {
					placeLeaves(world, pos.add(dX, dY, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
				}
			}
	}

	private void genSmallLeaves(World world, BlockPos pos) {
		for (int dX = -1; dX <= 1; dX++)
			for (int dZ = -1; dZ <= 1; dZ++)
				if (Math.abs(dX) != 1 || Math.abs(dZ) != 1)
					placeLeaves(world, pos.add(dX, 0, dZ), Math.abs(dX) <= 1 && Math.abs(dZ) <= 1);
	}
}
