package inc.a13xis.legacy.dendrology.world.gen.feature.hekur;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class LargeHekurTree extends NormalHekurTree
{
    public LargeHekurTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected void growTrunk(World world, Random random, BlockPos pos)
    {
        placeLog(world, pos);

        switch (random.nextInt(4))
        {
            case 0:
                placeLog(world, pos.east());
                placeLog(world, pos.add(1,1,0));
                largeDirect(world, random, 1, 0,pos.up(), 2, 5, 4, 3);
                break;

            case 1:
                placeLog(world, pos.south());
                placeLog(world, pos.add(0,1,1));
                largeDirect(world, random, 0, 1, pos.up(), 2, 5, 4, 3);
                break;

            case 2:
                placeLog(world, pos.west());
                placeLog(world, pos.add(-1,1,0));
                largeDirect(world, random, -1, 0, pos.up(), 2, 5, 4, 3);
                break;

            default:
                placeLog(world, pos.north());
                placeLog(world, pos.add(0,1,-1));
                largeDirect(world, random, 0, -1, pos.up(), 2, 5, 4, 3);
        }
    }
}
