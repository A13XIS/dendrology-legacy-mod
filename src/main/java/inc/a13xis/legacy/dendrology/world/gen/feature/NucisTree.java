package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NucisTree extends AbstractTree
{
    private int logDirection = 0;

    public NucisTree(boolean fromSapling) { super(fromSapling); }

    public NucisTree() { this(true); }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(15) + 8;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int level = 0; level < height; level++)
        {
            placeLog(world, pos.up(level));

            if (level > 3)
            {
                final int branchRarity = height / (level - 2) + 1;

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

        leafGen(world, pos.up(height));

        return true;
    }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    private void branch(World world, Random random, BlockPos pos, int height, int level, int dX, int dZ)
    {
        pos = pos.up(level);
        final int lengthToGo = height - pos.getY();

        int index = 0;
        while (index <= lengthToGo)
        {
            if (dX == -1 && random.nextInt(3) > 0)
            {
                pos = pos.west();
                logDirection = 4;

                if (dZ == 0 && random.nextInt(4) == 0) pos = pos.south(random.nextInt(3) - 1);
            }
            else if (dX == 1 && random.nextInt(3) > 0)
            {
                pos = pos.east();
                logDirection = 4;

                if (dZ == 0 && random.nextInt(4) == 0) pos = pos.south(random.nextInt(3) - 1);
            }

            if (dZ == -1 && random.nextInt(3) > 0)
            {
                pos = pos.north();
                logDirection = 8;

                if (dX == 0 && random.nextInt(4) == 0) pos = pos.east(random.nextInt(3) - 1);
            } else if (dZ == 1 && random.nextInt(3) > 0)
            {
                pos = pos.south();
                logDirection = 8;

                if (dX == 0 && random.nextInt(4) == 0) pos = pos.east(random.nextInt(3) - 1);
            }

            placeLog(world, pos);

            if (random.nextInt(3) > 0) pos.up();

            if (index == lengthToGo || random.nextInt(6) == 0)
            {
                placeLog(world, pos);
                leafGen(world, pos);
            }

            logDirection = 0;

            index++;
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
        {
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
    }

    @Override
    protected int getLogMetadata()
    {
        return super.getLogMetadata() | logDirection;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logDirection", logDirection).toString();
    }
}
