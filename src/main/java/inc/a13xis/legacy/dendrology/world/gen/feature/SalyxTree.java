package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import java.util.Random;

public class SalyxTree extends AbstractTree
{
    private int logDirection = 0;

    public SalyxTree(boolean fromSapling) { super(fromSapling); }

    public SalyxTree() { this(true); }

    @SuppressWarnings("OverlyComplexBooleanExpression")
    private static int calcK(int dX, int dZ)
    {
        if (dZ == -1 && dX == 0) return -1;

        if (dZ == 1 && dX != -1 || dZ == 0 && dX != 0) return 1;

        return 0;
    }

    @SuppressWarnings("OverlyComplexBooleanExpression")
    private static int calcM(int dX, int dZ)
    {
        if (dZ != 0 && dX == 0 || dZ == 0 && dX == 1) return 1;

        if (dZ == 0 && dX == -1 || dZ == 1 && dX == 1) return -1;

        return 0;
    }

    private static int calcN(int dX, int dZ)
    {
        if (dZ == 1) return 1;

        if (dZ == -1 || dZ == 0 && dX != 0) return -1;

        return 0;
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "OverlyLongMethod" })
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int size = rng.nextInt(6);

        if (isPoorGrowthConditions(world, pos, 6 + size / 2, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world, pos.down(), pos);

        for (int dX = -1; dX <= 1; dX++)
            for (int dZ = -1; dZ <= 1; dZ++)
                for (int dY = 0; dY <= 4; dY++)
                    placeLog(world, pos.add(dX,dY,dZ));

        for (int dY = 5; dY <= 6 + size / 2; dY++)
            placeLog(world, pos.up(dY));

        mainBranch(world, rng, pos.add(2,4,2), 1, 1, size);
        mainBranch(world, rng, pos.add(2,4,0), 1, 0, size);
        inner(world, rng, pos.add(1,5,1), 1, 1, size);
        inner(world, rng, pos.add(1,5,0), 1, 0, size);
        innerInner(world, rng, pos.up(6 + size / 2), 1, 1, size);

        mainBranch(world, rng, pos.add(2,4,-2), 1, -1, size);
        mainBranch(world, rng, pos.add(0,4,-2), 0, -1, size);
        inner(world, rng, pos.add(1,5,-1), 1, -1, size);
        inner(world, rng, pos.add(0,5,-1), 0, -1, size);
        innerInner(world, rng, pos.up(6 + size / 2), 1, -1, size);


        mainBranch(world, rng, pos.add(-2,4,-2), 1, 1, size);
        mainBranch(world, rng, pos.add(-2,4,0), 1, 0, size);
        inner(world, rng, pos.add(-1,5,-1), 1, 1, size);
        inner(world, rng, pos.add(-1,5,0), 1, 0, size);
        innerInner(world, rng, pos.up(6 + size / 2), 1, 1, size);

        mainBranch(world, rng, pos.add(-2,4,-2), 1, 1, size);
        mainBranch(world, rng, pos.add(0,4,2), 1, 0, size);
        inner(world, rng, pos.add(-1,5,1), 1, 1, size);
        inner(world, rng, pos.add(0,5,1), 1, 0, size);
        innerInner(world, rng, pos.up(6 + size / 2), 1, 1, size);

        return true;
    }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    private void mainBranch(World world, Random rand, BlockPos pos, int dX, int dZ, int size)
    {
        if (dX != 0) logDirection = 4;
        if (dZ != 0) logDirection = 8;

        final int j = dX == 1 ? 1 : -1;
        final int k = calcK(dX, dZ);
        final int m = calcM(dX, dZ);
        final int n = calcN(dX, dZ);

        final int spos = 2 * size + size / 2;
        int bend = 0;

        for (int i = 0; i < spos; i++)
        {
            placeLog(world, pos);
            placeLog(world, pos.down());

            if (dZ == 0) pos = pos.south(rand.nextInt(3) - 1);
            else if (dZ == 1) pos = pos.south(rand.nextInt(2));
            else pos = pos.north(rand.nextInt(2));

            if (dX == 0) pos = pos.east(rand.nextInt(3) - 1);
            else if (dX == 1) pos = pos.east(rand.nextInt(2));
            else pos = pos.west(rand.nextInt(2));

            if (bend == 0 && rand.nextInt(3) == 0)
            {
                pos = pos.up();
            } else if (bend == 2 && rand.nextInt(2) == 0)
            {
                pos = pos.down();
            }

            if (rand.nextInt(24) == 0)
            {
                final int currentLogDirection = logDirection;
                secFlag(world, rand, pos, j, k, size);
                secFlag(world, rand, pos, m, n, size);
                logDirection = currentLogDirection;
            }

            if (i == spos / 3) bend = 1;
            else if (i == 2 * spos / 3) bend = 2;

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, pos);
                placeLog(world, pos);
            }
        }
        logDirection = 0;
    }

    private void secFlag(World world, Random rand, BlockPos pos, int dX, int dZ, int size)
    {

        if (dX != 0) logDirection = 4;
        if (dZ != 0) logDirection = 8;

        for (int i = 0; i < 2 * size; i++)
        {
            pos = pos.up(rand.nextInt(3) - 1);

            if (dX == 1) pos = pos.east(rand.nextInt(2));
            else if (dX == -1) pos = pos.west(rand.nextInt(2));
            else pos = pos.east(rand.nextInt(3) - 1);

            if (dZ == 1) pos = pos.south(rand.nextInt(2));
            else if (dZ == -1) pos = pos.north(rand.nextInt(2));
            else pos = pos.south(rand.nextInt(3) - 1);

            placeLog(world, pos);

            if (rand.nextInt(4) > 0)
            {
                placeLog(world, pos);
                genLeaves(world, pos);
            }
        }
        logDirection = 0;
    }

    private void inner(World world, Random rand, BlockPos pos, int dX, int dZ, int size)
    {

        if (dX != 0) logDirection = 4;
        if (dZ != 0) logDirection = 8;

        int j = 5;

        for (int i = 0; i < 2 * size && j < 14; i++)
        {
            placeLog(world, pos);

            if (rand.nextInt(1 + i / 4) == 0)
            {
                pos = pos.up();
                j++;
            }

            if (dZ == 0) pos = pos.south(rand.nextInt(3) - 1);
            else if (dZ == 1) pos = pos.south(rand.nextInt(2));
            else pos = pos.north(rand.nextInt(2));

            if (dX == 0) pos = pos.east(rand.nextInt(3) - 1);
            else if (dX == 1) pos = pos.east(rand.nextInt(2));
            else pos = pos.west(rand.nextInt(2));

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, pos);
                placeLog(world, pos);
            }
        }

        logDirection = 0;
    }

    @SuppressWarnings("OverlyComplexMethod")
    private void innerInner(World world, Random rand, BlockPos pos, int dX, int dZ, int size)
    {

        if (dX != 0) logDirection = 4;
        if (dZ != 0) logDirection = 8;

        int j = 6 + size / 2;

        for (int i = 0; i < 2 * size + 1 && j < 16; i++)
        {
            placeLog(world, pos);

            pos = pos.up();
            j++;

            if (rand.nextInt(3) == 0) if (dX == -1) pos = pos.west();
            else if (dX == 1) pos = pos.east();

            if (rand.nextInt(3) == 0) if (dZ == -1) pos = pos.north();
            else if (dZ == 1) pos = pos.south();

            if (rand.nextInt(4) > 0)
            {
                genLeaves(world, pos);
                placeLog(world, pos);
            }
        }

        logDirection = 0;
    }

    private void genLeaves(World world, BlockPos pos)
    {
        placeLeaves(world, pos.up());
        placeLeaves(world, pos.up(2));

        for (int dY = 1; dY >= -2; dY--)
        {
            placeLeaves(world, pos.up(dY-1));

            for (int dX = -1; dX <= 1; dX++)
                for (int dZ = -1; dZ <= 1; dZ++)
                    if ((dX != 0 || dZ != 0) && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                        placeLeaves(world, pos.add(dX,dY,dZ));
        }
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logDirection", logDirection).toString();
    }
}
