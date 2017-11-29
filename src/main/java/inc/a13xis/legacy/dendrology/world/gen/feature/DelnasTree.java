package inc.a13xis.legacy.dendrology.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DelnasTree extends AbstractTree
{
    public DelnasTree(boolean fromSapling) { super(fromSapling); }

    public DelnasTree() { this(true); }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        final Random rng = new Random();
        rng.setSeed(rand.nextLong());

        final int height = rng.nextInt(5) + 6;

        if (isPoorGrowthConditions(world, pos, height, getSaplingBlock())) return false;

        final Block block = world.getBlockState(pos.down()).getBlock();
        block.onPlantGrow(world.getBlockState(pos.down()),world, pos.down(), pos);

        if (rng.nextInt(10) > 0) for (int dY = 0; dY <= height; dY++)
        {
            placeLog(world, pos.add(0,dY,0));

            if (dY == height) leafGen(world, pos.add(0,dY,0));
        }
        else switch (rand.nextInt(4))
        {
            case 0:
                growDirect(world, rng, pos, 1, 0, height);
                break;

            case 1:
                growDirect(world, rng, pos, 0, 1, height);
                break;

            case 2:
                growDirect(world, rng, pos, -1, 0, height);
                break;

            default:
                growDirect(world, rng, pos, 0, -1, height);
        }

        return true;
    }

    private void leafGen(World world, BlockPos pos)
    {
        for (int dX = -3; dX <= 3; dX++)
            for (int dZ = -3; dZ <= 3; dZ++)
            {
                if (Math.abs(dX) + Math.abs(dZ) <= 3 &&
                        !(Math.abs(dX) + Math.abs(dZ) == 3 && Math.abs(dX) != 0 && Math.abs(dZ) != 0))
                    placeLeaves(world, pos.add(dX,0,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
                if (Math.abs(dX) < 2 && Math.abs(dZ) < 2 && (Math.abs(dX) != 1 || Math.abs(dZ) != 1))
                    placeLeaves(world, pos.add(dX,1,dZ),Math.abs(dX)<=1&&Math.abs(dZ)<=1);
            }
    }

    private void growDirect(World world, Random rand, BlockPos pos, int dX, int dZ, int hight)
    {
        placeLog(world, pos);

        if (dX == 1) placeLog(world, pos.west());

        if (dX == -1) placeLog(world, pos.east());

        if (dZ == 1) placeLog(world, pos.north());

        if (dZ == -1) placeLog(world, pos.south());

        int addlRandomLengthX = 0;
        int addlRandomLengthZ = 0;
        for (int level = 0; level <= hight; level++)
        {
            if (dX == 1 && rand.nextInt(2 + addlRandomLengthX) == 0) pos = pos.east();

            if (dX == -1 && rand.nextInt(2 + addlRandomLengthX) == 0) pos = pos.west();

            if (dZ == 1 && rand.nextInt(2 + addlRandomLengthZ) == 0) pos = pos.south();

            if (dZ == -1 && rand.nextInt(2 + addlRandomLengthZ) == 0) pos = pos.north();

            addlRandomLengthX++;
            addlRandomLengthZ++;
            placeLog(world, pos.up(level));
        }
        leafGen(world, pos.up(hight));
    }
}
