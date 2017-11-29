package inc.a13xis.legacy.dendrology.world.gen.feature.cedrum;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LargeCedrumTree extends NormalCedrumTree
{
    public LargeCedrumTree(boolean fromSapling) { super(fromSapling); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());
        final int height = rng.nextInt(12) + 12;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        for (int level = height; level >= 0; level--)
        {
            placeLog(world, pos.up(level));

            if (level > 5 && level < height)
            {
                if (level == height - 1)
                {
                    leafGen(world, 2, pos.up(level));
                }


                if (level == height - 4 || level == height - 7 || level == height - 10 || level == height - 13)
                {
                    for (int next = 1; next < 3; next++)
                    {
                        logAxis = BlockLog.EnumAxis.X;
                        placeLog(world, pos.add(next,level-2,0));
                        placeLog(world, pos.add(-next,level-2,0));
                        logAxis = BlockLog.EnumAxis.Z;
                        placeLog(world, pos.add(0,level-2,next));
                        placeLog(world, pos.add(0,level-2,-next));
                        logAxis = BlockLog.EnumAxis.Y;
                    }

                    final int size = level == height - 4 ? 3 :
                            level == height - 7 ? 4 : level == height - 10 ? 5 : rng.nextInt(3) + 2;
                    leafGen(world, size, pos.up(level));
                }
            }

            if (level == height) leafTop(world, pos.up(level));
        }

        return true;
    }
}
