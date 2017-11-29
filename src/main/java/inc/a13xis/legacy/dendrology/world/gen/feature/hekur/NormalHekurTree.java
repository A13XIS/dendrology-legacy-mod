package inc.a13xis.legacy.dendrology.world.gen.feature.hekur;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Random;


@SuppressWarnings("OverlyComplexClass")
public class NormalHekurTree extends AbstractTree
{
    private BlockLog.EnumAxis logAxis = BlockLog.EnumAxis.Y;

    public NormalHekurTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean isPoorGrowthConditions(World world, BlockPos pos, int unused, IPlantable plantable)
    {
        final Block block = world.getBlockState(pos.down()).getBlock();
        return !block.canSustainPlant(world.getBlockState(pos.down()),world, pos.down(), EnumFacing.UP, plantable);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("logAxis", logAxis).toString();
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random random = new Random();
        random.setSeed(rand.nextLong());
        if (isPoorGrowthConditions(world, pos, 0, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos),world, pos.down(), pos);

        genRoots(world, random, pos);
        growTrunk(world, random, pos);

        return true;
    }

    private void genRoots(World world, Random random, BlockPos pos)
    {
        for (final ImmutablePair<Integer, Integer> branchDirection : BRANCH_DIRECTIONS)
            if (random.nextInt(3) == 0)
                genRoot(world, random, pos, branchDirection.getLeft(), branchDirection.getRight());

        genRoot(world, random, pos, 0, 0);
    }

    private void genRoot(World world, Random rand, BlockPos pos, int dX, int dZ)
    {
        setLogDirection(dX, dZ);

        for (int i = 0; i < 6; i++)
        {
            if (rand.nextInt(3) == 0)
            {
                if (dX == -1) pos = pos.west();

                if (dX == 1) pos = pos.east();

                if (dZ == -1) pos = pos.north();

                if (dZ == 1) pos = pos.south();
            }

            placeRoot(world, pos);

            pos = pos.down();
        }

        clearLogDirection();
    }

    private void setLogDirection(int dX, int dZ)
    {
        if (dX != 0) logAxis = BlockLog.EnumAxis.X;

        if (dZ != 0) logAxis = BlockLog.EnumAxis.Z;
    }

    private boolean canBeReplacedByRoot(World world, BlockPos pos)
    {
        final Material material = world.getBlockState(pos).getMaterial();

        return canBeReplacedByLog(world, pos) || material.equals(Material.SAND) || material.equals(Material.GROUND);
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean placeRoot(World world, BlockPos pos)
    {
        if (canBeReplacedByRoot(world, pos))
        {
            setBlockAndNotifyAdequately(world, pos, getLogBlock().getStateFromMeta(getLogMetadata()));
            return true;
        }
        return false;
    }

    void growTrunk(World world, Random random, BlockPos pos)
    {
        placeLog(world, pos);

        switch (random.nextInt(4))
        {
            case 0:
                placeLog(world, pos.up(2));
                placeLog(world, pos.add(-1,1,0));
                largeDirect(world, random, 1, 0, pos.up(2), 1, 2, 0, 2);
                break;

            case 1:
                placeLog(world, pos.up());
                placeLog(world, pos.up(2));
                placeLog(world, pos.add(0,1,-1));
                largeDirect(world, random, 0, 1, pos.up(2), 1, 2, 0, 2);
                break;

            case 2:
                placeLog(world, pos.up());
                placeLog(world, pos.up(2));
                placeLog(world, pos.add(1,1,0));
                largeDirect(world, random, -1, 0, pos.up(2), 1, 2, 0, 2);
                break;

            default:
                placeLog(world, pos.up());
                placeLog(world, pos.up(2));
                placeLog(world, pos.add(0,1,1));
                largeDirect(world, random, 0, -1, pos.up(), 1, 2, 0, 2);
        }
    }

    void largeDirect(World world, Random rand, int dX, int dZ, BlockPos pos, int size, int splitCount,int splitCount1, int splitCount2)
    {
        setLogDirection(dX, dZ);

        int dSize = 0;

        if (size == 2) dSize = 2;

        for (int next = 0; next <= 5 * size; next++)
        {
            if (size == 1) pos = pos.up();

            placeLog(world, pos, logAxis);

            if (next <= 9 && size == 2) placeLog(world, pos.add(-dX,0,-dZ), logAxis);

            if (next == 5 * size) branchAndLeaf(world, pos.up());

            if (size == 2) pos = pos.up();

            pos = pos.east(dX);
            pos = pos.south(dZ);

            if (next == splitCount)
            {
                firstBranchSplit(world, rand, pos, dX, dZ, splitCount);
                secondBranchSplit(world, rand, pos, dX, dZ, splitCount);
            }

            if (next == 3 * size && size == 2)
            {
                fifthBranchSplit(world, rand, pos, dX, dZ, splitCount1);
                sixthBranchSplit(world, rand, pos, dX, dZ, splitCount1);
            }

            if (next == 3 * size)
            {
                thirdBranchSplit(world, rand, pos, dX, dZ, 4 * size - dSize);
                fourthBranchSplit(world, rand, pos, dX, dZ, 4 * size - dSize);
            }

            if (next == 4 * size)
            {
                fifthBranchSplit(world, rand, pos, dX, dZ, splitCount2);
                sixthBranchSplit(world, rand, pos, dX, dZ, splitCount2);
            }
        }
        clearLogDirection();
    }

    private void firstBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int splitCount)
    {
        for (int i = 0; i <= splitCount; i++)
        {
            if (dX != 0)
            {
                if (rand.nextInt(5) > 0) if (dX == 1) pos = pos.west();
                else pos = pos.east();
                pos = pos.south(rand.nextInt(2));
            }

            if (dZ == 1)
            {
                pos = pos.west(rand.nextInt(2));

                if (rand.nextInt(5) > 0) pos = pos.north();
            } else if (dZ == -1)
            {
                pos = pos.east(rand.nextInt(2));

                if (rand.nextInt(5) > 0) pos = pos.south();
            }

            pos = pos.up();
            placeLog(world, pos,logAxis);

            if (i == splitCount) branchAndLeaf(world, pos);
        }
    }

    private void secondBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int splitCount)
    {
        for (int i = 0; i <= splitCount; i++)
        {
            if (dX != 0)
            {
                if (rand.nextInt(5) > 0) if (dX == 1) pos = pos.west();
                else pos = pos.east();
                pos = pos.north(rand.nextInt(2));
            }

            if (dZ == 1)
            {
                pos = pos.east(rand.nextInt(2));

                if (rand.nextInt(5) > 0) pos = pos.north();
            } else if (dZ == -1)
            {
                pos = pos.west(rand.nextInt(2));

                if (rand.nextInt(5) > 0) pos = pos.south();
            }

            pos = pos.up();
            placeLog(world, pos,logAxis);

            if (i == splitCount) branchAndLeaf(world, pos);
        }
    }

    private void thirdBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int length)
    {

        for (int i = 0; i <= length; i++)
        {
            if (dX != 0)
            {
                if (dX == 1) pos = pos.east(rand.nextInt(2));
                else pos = pos.west(rand.nextInt(2));
                pos = pos.south(rand.nextInt(2));
            }

            if (dZ != 0)
            {
                if (dZ == 1) pos = pos.south(rand.nextInt(2));
                else pos = pos.north(rand.nextInt(2));
                pos = pos.east(rand.nextInt(2));
            }

            if (i >= 3) pos = pos.up(rand.nextInt(2));

            placeLog(world, pos,logAxis);

            if (i == length) branchAndLeaf(world, pos);
        }
    }

    private void fourthBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int length)
    {

        for (int i = 0; i <= length; i++)
        {
            if (dX != 0)
            {
                if (dX == 1) pos = pos.east(rand.nextInt(2));
                else pos = pos.west(rand.nextInt(2));
                pos = pos.north(rand.nextInt(2));
            }

            if (dZ != 0)
            {
                if (dZ == 1) pos = pos.south(rand.nextInt(2));
                else pos = pos.north(rand.nextInt(2));

                pos = pos.west(rand.nextInt(2));
            }
            if (i >= 3) pos = pos.up(rand.nextInt(2));

            placeLog(world, pos,logAxis);

            if (i == length) branchAndLeaf(world, pos);
        }
    }

    private void fifthBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int splitCount2)
    {

        for (int i = 0; i <= splitCount2; i++)
        {
            if (dX == 1)
            {
                pos = pos.west(rand.nextInt(2));
                pos = pos.south(rand.nextInt(2));
            }

            if (dX == -1)
            {
                pos = pos.east(rand.nextInt(2));
                pos = pos.south(rand.nextInt(2));
            }

            if (dZ == 1)
            {
                pos = pos.west(rand.nextInt(2));
                pos = pos.north(rand.nextInt(2));
            }

            if (dZ == -1)
            {
                pos = pos.east(rand.nextInt(2));
                pos = pos.south(rand.nextInt(2));
            }

            pos = pos.up();
            placeLog(world, pos,logAxis);

            if (i == splitCount2) branchAndLeaf(world, pos);
        }
    }

    private void sixthBranchSplit(World world, Random rand, BlockPos pos, int dX, int dZ, int splitCount2)
    {
        for (int i = 0; i <= splitCount2; i++)
        {
            if (dX != 0)
            {
                if (dX == 1)
                {
                    pos = pos.west(rand.nextInt(2));
                } else
                {
                    pos = pos.east(rand.nextInt(2));
                }
                pos = pos.north(rand.nextInt(2));
            }

            if (dZ == 1)
            {
                pos = pos.east(rand.nextInt(2));
                pos = pos.north(rand.nextInt(2));
            } else if (dZ == -1)
            {
                pos = pos.west(rand.nextInt(2));
                pos = pos.south(rand.nextInt(2));
            }

            pos = pos.up();
            placeLog(world, pos,logAxis);

            if (i == splitCount2) branchAndLeaf(world, pos);
        }
    }

    private void clearLogDirection() {
        logAxis = BlockLog.EnumAxis.Y;}

    private void branchAndLeaf(World world, BlockPos pos)
    {
        placeLog(world, pos,logAxis);

        for (int dX = -3; dX <= 3; dX++)
        {
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2))
                    placeLeaves(world, pos.add(dX,0,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                    placeLeaves(world,pos.add(dX,1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
            }
        }
    }
}
