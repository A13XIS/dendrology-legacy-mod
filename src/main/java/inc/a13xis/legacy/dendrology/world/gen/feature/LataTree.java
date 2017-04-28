package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LataTree extends AbstractTree
{
    private BlockLog.EnumAxis logAxis = BlockLog.EnumAxis.Y;

    public LataTree(boolean fromSapling) { super(fromSapling); }

    public LataTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(15) + 6;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int level = 0; level <= height; level++)
        {
            if (level == height) leafGen(world, pos.up(level));
            else placeLog(world, pos.up(level));

            if (level > 3 && level < height)
            {
                final int branchRarity = height / level + 1;

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, -1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, 1, 0);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, 0, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, 0, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, -1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, -1, -1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, 1, 1);

                if (rng.nextInt(branchRarity) == 0) branch(world, rng, pos, height, level, 1, -1);
            }
        }

        return true;
    }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    private void branch(World world, Random rand, BlockPos pos, int treeHeight, int branchLevel, int dX, int dZ)
    {
        final int length = treeHeight - branchLevel;

        pos = pos.up(branchLevel);

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                pos = pos.west();
                logAxis = BlockLog.EnumAxis.X;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            } else if (dX == 1 && rand.nextInt(3) > 0)
            {
                pos=pos.east();
                logAxis = BlockLog.EnumAxis.X;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                pos = pos.north();
                logAxis = BlockLog.EnumAxis.Z;

                if (dX == 0 && rand.nextInt(4) == 0) pos = pos.east(rand.nextInt(3) - 1);
            } else if (dZ == 1 && rand.nextInt(3) > 0)
            {
                pos = pos.south();
                logAxis = BlockLog.EnumAxis.Z;

                if (dX == 0 && rand.nextInt(4) == 0) pos = pos.east(rand.nextInt(3) - 1);
            }

            placeLog(world, pos,logAxis);
            logAxis = BlockLog.EnumAxis.Y;

            if (rand.nextInt(3) == 0)
            {
                leafGen(world, pos);
            }

            if (rand.nextInt(3) > 0)
            {
                pos = pos.up();
            }

            if (i == length)
            {
                placeLog(world, pos);
                leafGen(world, pos);
            }
        }
    }

    @SuppressWarnings({
            "MethodWithMoreThanThreeNegations",
            "MethodWithMultipleLoops",
            "OverlyComplexBooleanExpression"
    })
    private void leafGen(World world, BlockPos pos)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, pos.add(dX,0,dZ));

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, pos.add(dX,1,dZ));
                    placeLeaves(world, pos.add(dX,-1,dZ));
                }

                if (Math.abs(dX) + Math.abs(dZ) < 2)
                {
                    placeLeaves(world, pos.add(dX,2,dZ));
                    placeLeaves(world, pos.add(dX,-2,dZ));
                }
            }
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logAxis", logAxis).toString();
    }
}
