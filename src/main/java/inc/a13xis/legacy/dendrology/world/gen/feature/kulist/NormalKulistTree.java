package inc.a13xis.legacy.dendrology.world.gen.feature.kulist;

import com.google.common.base.MoreObjects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NormalKulistTree extends AbstractTree
{
    private BlockLog.EnumAxis logAxis = BlockLog.EnumAxis.Y;

    public NormalKulistTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean canBeReplacedByLog(World world, BlockPos pos)
    {
        return super.canBeReplacedByLog(world, pos) || world.getBlockState(pos).getMaterial().equals(Material.WATER);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("logAxis", logAxis.name()).toString();
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(5) + 6;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int level = 0; level <= height; level++)
        {
            placeLog(world, pos.up(level));

            if (level == height) leafGen(world, pos.up(level));

            if (level > 2 && level < height)
            {
                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, -1, 0);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, 1, 0);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, 0, -1);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, 0, 1);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, -1, 1);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, -1, -1);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, 1, 1);

                if (rng.nextInt(6) == 0) branch(world, rng, pos, height, level, 1, -1);
            }
        }
        return true;
    }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    void branch(World world, Random rand, BlockPos pos, int height, int level, int dX, int dZ)
    {
        pos.up(level);
        final int length = height - level;

        for (int i = 0; i <= length; i++)
        {
            if (dX == -1 && rand.nextInt(3) > 0)
            {
                pos = pos.west();
                logAxis = BlockLog.EnumAxis.X;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            }

            if (dX == 1 && rand.nextInt(3) > 0)
            {
                pos = pos.east();
                logAxis = BlockLog.EnumAxis.X;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                pos = pos.north();
                logAxis = BlockLog.EnumAxis.Z;

                if (dX == 0 && rand.nextInt(4) == 0) pos = pos.east(rand.nextInt(3) - 1);
            }

            if (dZ == 1 && rand.nextInt(3) > 0)
            {
                pos = pos.south();
                logAxis = BlockLog.EnumAxis.Z;

                if (dX == 0 && rand.nextInt(4) == 0) pos.east(rand.nextInt(3) - 1);
            }

            placeLog(world, pos);
            logAxis = BlockLog.EnumAxis.Y;

            if (rand.nextInt(3) > 0) pos.up();

            if (i == length)
            {
                placeLog(world, pos,logAxis);
                leafGen(world, pos);
            }
        }
    }

    void leafGen(World world, BlockPos pos)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if ((Math.abs(dX) != 3 || Math.abs(dZ) != 3) && (Math.abs(dX) != 2 || Math.abs(dZ) != 3) && (Math.abs(dX) != 3 || Math.abs(dZ) != 2)) placeLeaves(world, pos.add(dX,0,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);

                if (Math.abs(dX) < 3 && Math.abs(dZ) < 3 && (Math.abs(dX) != 2 || Math.abs(dZ) != 2))
                {
                    placeLeaves(world, pos.add(dX,1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                    placeLeaves(world, pos.add(dX,-1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                }

                if (Math.abs(dX) + Math.abs(dZ) < 2)
                {
                    placeLeaves(world, pos.add(dX,2,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                    placeLeaves(world, pos.add(dX,-2,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                }
            }
    }
}
