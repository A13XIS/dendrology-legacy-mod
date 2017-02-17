package inc.a13xis.legacy.dendrology.world.gen.feature;

import net.minecraft.block.Block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TuopaTree extends AbstractTree
{
    public TuopaTree(boolean fromSapling) { super(fromSapling); }

    public TuopaTree() { this(true); }

    @SuppressWarnings({
            "OverlyComplexMethod",
            "OverlyLongMethod",
            "OverlyComplexBooleanExpression",
            "MethodWithMoreThanThreeNegations",
            "MethodWithMultipleLoops"
    })
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height =
                1 + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextInt(7) < 2 ? 1 : 0) + (rng.nextBoolean() ? 1 : 0);

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int level = 0; level <= 6 * height + 1; level++)
        {
            if (level != 6 * height + 1) placeLog(world, pos.up(level));

            if (height == 1 && level > 2) for (int dX = -1; dX <= 1; dX++)
            {
                for (int dZ = -1; dZ <= 1; dZ++)
                    if (Math.abs(dX) != 1 || Math.abs(dZ) != 1) placeLeaves(world, pos.add(dX,level,dZ));
            }

            if (height == 2 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, pos.add(dX,level,dZ));

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level == 7) placeLeaves(world, pos.add(dX,7,dZ));

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height - 1 && level > 3)
                        placeLeaves(world, pos.add(dX,level,dZ));
                }

            if (height == 3 && level > 2) for (int dX = -2; dX <= 2; dX++)
                for (int dZ = -2; dZ <= 2; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world,pos.add(dX,level,dZ));

                    if ((Math.abs(dX) != 2 || Math.abs(dZ) != 2) && (Math.abs(dX) != 2 || Math.abs(dZ) != 1) &&
                            (Math.abs(dX) != 1 || Math.abs(dZ) != 2) && level <= 6 * height && level > 3)
                        placeLeaves(world, pos.add(dX,level,dZ));
                }

            if (height == 4 && level > 2) for (int dX = -3; dX <= 3; dX++)
                for (int dZ = -3; dZ <= 3; dZ++)
                {
                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, pos.add(dX,level,dZ));

                    if (Math.abs(dX) <= 1 && Math.abs(dZ) <= 1 && level <= 14 && level >= 2)
                        placeLeaves(world, pos.add(dX,level,dZ));

                    if (Math.abs(dX) <= 2 && Math.abs(dZ) <= 2 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2) &&
                            (level == 6 * height || level == 5)) placeLeaves(world, pos.add(dX,level,dZ));

                    if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2) &&
                            (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && level <= 6 * height - 1 && level > 5)
                        placeLeaves(world, pos.add(dX,level,dZ));
                }
        }

        return true;
    }
}
