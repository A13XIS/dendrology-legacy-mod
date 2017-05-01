package inc.a13xis.legacy.dendrology.world.gen.feature.ewcaly;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LargeEwcalyTree extends AbstractTree
{
    private int logDirection = 0;

    public LargeEwcalyTree(boolean fromSapling) { super(fromSapling); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(24) + 8;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int dy = 0; dy <= height; dy++)
            placeLog(world, pos.up(dy));

        int size = 1;

        for (BlockPos pos1 = pos.up(height/2); pos1.getY() <= pos.up(height).getY(); pos1 = pos1.up())
        {
            if (rng.nextInt(5) > 2 || pos1.getY() == pos.up(height).getY())
            {
                if (rng.nextInt(20) < 1) size = 2;

                if (rng.nextInt(4) == 0 && pos1.getY() - pos.getY() > 10 && pos1.getY() - pos.getY() < 20) size = 2;

                if (pos1.getY() - pos.getY() >= 20) size = 3;

                for (int dX = -size; dX <= size; dX++)
                    for (int dZ = -size; dZ <= size; dZ++)
                    {
                        placeLeaves(world, new BlockPos(pos.getX() + dX, pos1.getY(), pos.getZ() + dZ));
                        if (size != Math.abs(dX) || size != Math.abs(dZ)) placeLeaves(world, new BlockPos(pos.getX() + dX, pos1.getY(), pos.getZ() + dZ));

                        if (size == 3 &&
                                (Math.abs(dX) == 3 && Math.abs(dZ) == 2 || Math.abs(dX) == 2 && Math.abs(dZ) == 3))
                            setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + dX, pos1.getY(), pos.getZ() + dZ), Blocks.AIR.getDefaultState());

                        if (pos1.getY() == pos.getY() + height && Math.abs(dX) < 3 && Math.abs(dZ) < 3 &&
                                (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                        {
                            if (size > 1) placeLeaves(world, new BlockPos(pos.getX() + dX, pos1.up().getY(), pos.getZ() + dZ));

                            if (size == 1 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                                placeLeaves(world, new BlockPos(pos.getX() + dX, pos1.up().getY(), pos.getZ() + dZ));
                        }
                    }
            }
        }

        for (int dY = height / 2; dY <= height - 5; dY++)
        {
            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), -1, 0, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), 1, 0, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), 0, -1, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), 0, 1, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), -1, 1, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), -1, -1, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), 1, 1, height);

            if (rng.nextInt(9) == 0) branches(world, pos.up(dY), 1, -1, height);
        }

        return true;
    }

    private void branches(World world, BlockPos pos, int dX, int dZ, int height)
    {
        for (int i = 0; i < 8; i++)
        {
            if (dX == -1)
            {
                pos = pos.west();
                logDirection = 4;
            }

            if (dX == 1)
            {
                pos=pos.east();
                logDirection = 4;
            }

            if (dZ == -1)
            {
                pos=pos.north();
                logDirection = 8;
            }

            if (dZ == 1)
            {
                pos=pos.south();
                logDirection = 8;
            }

            placeLog(world, pos);
            logDirection = 0;

            if ((i == 4 || i == 7) && height >= 13) genLeaves(world, pos);

            if ((i == 4 || i == 7) && height < 13) genLeavesS(world, pos);

            pos=pos.up();
        }
    }

    private void genLeaves(World world, BlockPos pos)
    {
        for (int dX = -3; dX <= 3; dX++)
        {
            for (int dY = -3; dY <= 3; dY++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dY) != 3) && (Math.abs(dX) != 2 || Math.abs(dY) != 3) &&
                        (Math.abs(dX) != 3 || Math.abs(dY) != 2)) placeLeaves(world, pos.add(dX,0,dY));

                if (Math.abs(dX) < 3 && Math.abs(dY) < 3 && (Math.abs(dX) != 2 || Math.abs(dY) != 2))
                {
                    placeLeaves(world, pos.add(dX,-1,dY));
                    placeLeaves(world, pos.add(dX,1,dY));
                }
            }
        }
    }

    private void genLeavesS(World world, BlockPos pos)
    {
        for (int dX = -2; dX <= 2; dX++)
        {
            for (int dY = -2; dY <= 2; dY++)
            {
                if (Math.abs(dX) != 2 || Math.abs(dY) != 2) placeLeaves(world, pos.add(dX,0,dY));

                if (Math.abs(dX) < 2 && Math.abs(dY) < 2 && (Math.abs(dX) != 1 || Math.abs(dY) != 1))
                {
                    placeLeaves(world, pos.add(dX,1,dY));
                    placeLeaves(world, pos.add(dX,-1,dY));
                }
            }
        }
    }

    @Override
    protected int getLogMetadata() { return super.getLogMetadata() | logDirection; }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logDirection", logDirection).toString();
    }
}
