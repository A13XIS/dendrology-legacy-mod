package inc.a13xis.legacy.dendrology.world.gen.feature.ewcaly;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NormalEwcalyTree extends AbstractTree
{
    private BlockLog.EnumAxis logAxis = BlockLog.EnumAxis.Y;

    public NormalEwcalyTree(boolean fromSapling) { super(fromSapling); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(24) + 2;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int dY = 0; dY <= height; dY++)
            placeLog(world, pos.up(dY));

        int size = 1;

        for (int y1 = pos.getY() + height / 2; y1 <= pos.getY() + height; y1++)
            if (rng.nextInt(5) > 2 || y1 == pos.getY() + height)
            {
                if (rng.nextInt(20) < 1) size = 2;

                if (rng.nextInt(4) == 0 && y1 - pos.getY() > 10 && y1 - pos.getY() < 20) size = 2;

                if (y1 - pos.getY() >= 20) size = 3;

                for (int dX = -size; dX <= size; dX++)
                    for (int dZ = -size; dZ <= size; dZ++)
                    {
                        placeLeaves(world, new BlockPos(pos.getX() + dX, y1, pos.getZ() + dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);

                        if (size == 3 &&
                                (Math.abs(dX) == 3 && Math.abs(dZ) == 2 || Math.abs(dX) == 2 && Math.abs(dZ) == 3))
                        {
                            setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + dX, y1, pos.getZ() + dZ), Blocks.AIR.getDefaultState());
                        }

                        if (y1 == pos.getY() + height && Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                        {
                            if (size > 1)
                            {
                                placeLeaves(world, new BlockPos(pos.getX() + dX, y1 + 1, pos.getZ() + dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                            }

                            if (size == 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                            {
                                placeLeaves(world, new BlockPos(pos.getX() + dX, y1 + 1, pos.getZ() + dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                            }
                        }
                    }
            }

        for (int dY = height / 2; dY <= height - 3; dY++)
        {
            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), -1, 0, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), 1, 0, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), 0, -1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), 0, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), -1, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), -1, -1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), 1, 1, height);
            }

            if (rng.nextInt(11) == 0)
            {
                branches(world, rng, pos.up(dY), 1, -1, height);
            }
        }

        return true;
    }

    private void branches(World world, Random rand, BlockPos pos, int dX, int dZ, int height)
    {
        for (int i = 0; i < 5; i++)
        {
            if (dX == -1 && rand.nextInt(3) == 0)
            {
                pos = pos.west();
                logAxis = BlockLog.EnumAxis.X;
            }

            if (dX == 1 && rand.nextInt(3) == 0)
            {
                pos = pos.east();
                logAxis = BlockLog.EnumAxis.X;
            }

            if (dZ == -1 && rand.nextInt(3) == 0)
            {
                pos = pos.north();
                logAxis = BlockLog.EnumAxis.Z;
            }

            if (dZ == 1 && rand.nextInt(3) == 0)
            {
                pos = pos.south();
                logAxis = BlockLog.EnumAxis.Z;
            }

            placeLog(world, pos,logAxis);
            logAxis = BlockLog.EnumAxis.Y;

            if (i == 4 && height >= 18)
            {
                genLeaves(world, pos);
            }

            if (i == 4 && height < 18)
            {
                genLeavesS(world, pos);
            }

            pos = pos.up();
        }
    }

    private void genLeaves(World world, BlockPos pos)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2))
                    placeLeaves(world, pos.add(dX,0,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, pos.add(dX,-1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                    placeLeaves(world, pos.add(dX,1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                }
            }
    }

    private void genLeavesS(World world, BlockPos pos)
    {
        for (int dX = -2; dX <= 2; dX++)
            for (int dZ = -2; dZ <= 2; dZ++)
            {
                if (Math.abs(dX) != 2 || Math.abs(dZ) != 2)
                    placeLeaves(world, pos.add(dX,0,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);

                if (Math.abs(dX) < 2 && Math.abs(dZ) < 2 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                {
                    placeLeaves(world, pos.add(dX,1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                    placeLeaves(world, pos.add(dX,-1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                }
            }
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("logDirection", logAxis.name()).toString();
    }
}
