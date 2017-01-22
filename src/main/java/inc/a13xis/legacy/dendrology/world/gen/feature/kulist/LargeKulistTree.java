package inc.a13xis.legacy.dendrology.world.gen.feature.kulist;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LargeKulistTree extends NormalKulistTree
{
    public LargeKulistTree(boolean fromSapling) { super(fromSapling); }

    @SuppressWarnings({ "OverlyComplexMethod", "OverlyLongMethod" })
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(9) + 9;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world, pos.down(), pos);

        for (int level = 0; level <= height; level++)
        {
            placeLog(world, pos.up(level));

            if (level == height) leafGen(world, pos.up(level));

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

}
