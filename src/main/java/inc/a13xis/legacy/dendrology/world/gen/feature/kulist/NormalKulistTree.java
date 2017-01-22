package inc.a13xis.legacy.dendrology.world.gen.feature.kulist;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NormalKulistTree extends AbstractTree
{
    private int logDirection = 0;

    public NormalKulistTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected boolean canBeReplacedByLog(World world, BlockPos pos)
    {
        return super.canBeReplacedByLog(world, pos) || world.getBlockState(pos).getBlock().getMaterial().equals(Material.water);
    }

    @Override
    protected int getLogMetadata() {return super.getLogMetadata() | logDirection;}

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("logDirection", logDirection).toString();
    }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(5) + 6;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world, pos.down(), pos);

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
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            }

            if (dX == 1 && rand.nextInt(3) > 0)
            {
                pos = pos.east();
                logDirection = 4;

                if (dZ == 0 && rand.nextInt(4) == 0) pos = pos.south(rand.nextInt(3) - 1);
            }

            if (dZ == -1 && rand.nextInt(3) > 0)
            {
                pos = pos.north();
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) pos = pos.east(rand.nextInt(3) - 1);
            }

            if (dZ == 1 && rand.nextInt(3) > 0)
            {
                pos = pos.south();
                logDirection = 8;

                if (dX == 0 && rand.nextInt(4) == 0) pos.east(rand.nextInt(3) - 1);
            }

            placeLog(world, pos);
            logDirection = 0;

            if (rand.nextInt(3) > 0) pos.up();

            if (i == length)
            {
                placeLog(world, pos);
                leafGen(world, pos);
            }
        }
    }

    void leafGen(World world, BlockPos pos)
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
}
