package inc.a13xis.legacy.dendrology.world.gen.feature.vanilla;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.world.gen.feature.AbstractTree;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public abstract class AbstractLargeVanillaTree extends AbstractTree
{
    private static final byte[] otherCoordPairs = { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };
    private static final double HEIGHT_ATTENUATION = 0.618D;
    private static final double BRANCH_SLOPE = 0.381D;
    private static final double SCALE_WIDTH = 1.1D;
    private static final double LEAF_DENSITY = 1.0D;
    private static final int HEIGHT_LIMIT_LIMIT = 12;
    private static final int LEAF_DISTANCE_LIMIT = 4;
    @SuppressWarnings("UnsecureRandomNumberGeneration")
    private final Random rng = new Random();
    private final int[] basePos = { 0, 0, 0 };
    private int heightLimit = 0;
    private int[][] leafNodes = null;
    private int logMetaMask = 0;

    protected AbstractLargeVanillaTree(boolean fromSapling) { super(fromSapling); }

    @SuppressWarnings("NestedConditionalExpression")
    private static float leafSize(int distance)
    {
        return distance >= 0 && distance < LEAF_DISTANCE_LIMIT ?
                distance != 0 && distance != LEAF_DISTANCE_LIMIT - 1 ? 3.0F : 2.0F : -1.0F;
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "NonBooleanMethodNameMayNotStartWithQuestion" })
    private int checkBlockLine(World world, int[] start, int[] end)
    {
        final int[] current = { 0, 0, 0 };

        int xIndex = 0;
        for (int i = 0; i < 3; ++i)
        {
            current[i] = end[i] - start[i];

            if (Math.abs(current[i]) > Math.abs(current[xIndex])) xIndex = i;
        }

        if (current[xIndex] == 0) return -1;

        final int yIndex = otherCoordPairs[xIndex];
        final int zindex = otherCoordPairs[xIndex + 3];

        final int xVariance = current[xIndex] > 0 ? 1 : -1;

        final double ySlope = current[yIndex] / (double) current[xIndex];
        final double zSlope = current[zindex] / (double) current[xIndex];
        final int[] coord = { 0, 0, 0 };
        int dX = 0;

        final int xLimit = current[xIndex] + xVariance;
        while (dX != xLimit)
        {
            coord[xIndex] = start[xIndex] + dX;
            coord[yIndex] = MathHelper.floor_double(start[yIndex] + dX * ySlope);
            coord[zindex] = MathHelper.floor_double(start[zindex] + dX * zSlope);

            if (!isReplaceable(world, new BlockPos(coord[0], coord[1], coord[2])))
            {
                return Math.abs(dX);
            }
            dX += xVariance;
        }

        return -1;
    }

    @Override
    protected boolean isPoorGrowthConditions(World world, BlockPos pos, int height, IPlantable plantable)
    {
        if (pos.getY() < 1 || pos.getY() + height + 1 > world.getHeight()) return true;

        final Block block = world.getBlockState(new BlockPos(basePos[0], basePos[1] - 1, basePos[2])).getBlock();
        return !block.canSustainPlant(world, pos.down(), EnumFacing.UP, plantable) || !hasRoomToGrow(world, pos, height);

    }

    @Override
    protected boolean hasRoomToGrow(World world, BlockPos pos, int height)
    {
        final int[] bottom = { pos.getX(), pos.getY(), pos.getZ() };
        final int[] top = { pos.getX(), pos.up( heightLimit - 1).getY(), pos.getZ() };

        final int maxHeight = checkBlockLine(world, bottom, top);

        if (maxHeight == -1) return true;

        if (maxHeight < 6) return false;

        heightLimit = maxHeight;
        return true;
    }

    @SuppressWarnings("FinalMethod")
    @Override
    protected final int getLogMetadata()
    {
        return getUnmaskedLogMeta() | logMetaMask;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("rng", rng).add("basePos", basePos).add("heightLimit", heightLimit)
                .add("leafNodes", leafNodes).add("logMetaMask", logMetaMask).toString();
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        rng.setSeed(rand.nextLong());
        basePos[0] = pos.getX();
        basePos[1] = pos.getY();
        basePos[2] = pos.getZ();

        heightLimit = 5 + rand.nextInt(HEIGHT_LIMIT_LIMIT);

        if (!isPoorGrowthConditions(world, pos, heightLimit, getSaplingBlock())){

        final int height = generateLeafNodeList(world);
        generateLeaves(world);
        generateTrunk(world, pos, height);
        generateLeafNodeBases(world, pos);
        return true;
        }
        return false;
    }

    private void generateLeafNodeBases(World world, BlockPos pos)
    {
        int i = 0;
        final int j = leafNodes.length;

        final int[] aint = { pos.getX(), pos.getY(), pos.getZ() };
        while (i < j)
        {
            final int[] aint1 = leafNodes[i];
            final int[] aint2 = { aint1[0], aint1[1], aint1[2] };
            aint[1] = aint1[3];
            final int k = aint[1] - basePos[1];

            if (leafNodeNeedsBase(k))
            {
                placeBlockLine(world, aint, aint2);
            }
            ++i;
        }
    }

    private boolean leafNodeNeedsBase(int distance)
    {
        return distance >= heightLimit * 0.2D;
    }

    private void generateTrunk(World world, BlockPos pos, int height)
    {
        final int[] bottom = { pos.getX(), pos.getY(), pos.getZ() };
        final int[] top = { pos.getX(), pos.up(height).getY(), pos.getZ() };
        placeBlockLine(world, bottom, top);
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "OverlyLongMethod" })
    private void placeBlockLine(World world, int[] start, int[] end)
    {
        final int[] aint2 = { 0, 0, 0 };
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0)
        {
            aint2[b0] = end[b0] - start[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
            {
                b1 = b0;
            }
        }

        if (aint2[b1] != 0)
        {
            final byte b2 = otherCoordPairs[b1];
            final byte b3 = otherCoordPairs[b1 + 3];

            final int b4 = aint2[b1] > 0 ? 1 : -1;

            final double d0 = aint2[b2] / (double) aint2[b1];
            final double d1 = aint2[b3] / (double) aint2[b1];
            final int[] aint3 = { 0, 0, 0 };
            int i = 0;

            final int j = aint2[b1] + b4;
            while (i != j)
            {
                aint3[b1] = MathHelper.floor_double((start[b1] + i) + 0.5D);
                aint3[b2] = MathHelper.floor_double(start[b2] + i * d0 + 0.5D);
                aint3[b3] = MathHelper.floor_double(start[b3] + i * d1 + 0.5D);
                final int xDistance = Math.abs(aint3[0] - start[0]);
                final int zDistance = Math.abs(aint3[2] - start[2]);
                final int distance = Math.max(xDistance, zDistance);

                if (distance > 0) if (xDistance == distance) logMetaMask = 4;
                else if (zDistance == distance) logMetaMask = 8;

                placeLog(world, new BlockPos(aint3[0], aint3[1], aint3[2]));
                logMetaMask = 0;
                i += b4;
            }
        }
    }

    protected abstract int getUnmaskedLogMeta();

    private void generateLeaves(World world)
    {
        int node = 0;

        final int length = leafNodes.length;
        while (node < length)
        {
            generateLeafNode(world, new BlockPos(leafNodes[node][0], leafNodes[node][1], leafNodes[node][2]));
            ++node;
        }
    }

    private void generateLeafNode(World world, BlockPos pos)
    {
        int y1 = pos.getY();

        while (y1 < pos.getY() + LEAF_DISTANCE_LIMIT)
        {
            final float size = leafSize(y1 - pos.getY());
            genTreeLayer(world, new BlockPos(pos.getX(), y1, pos.getZ()), size, (byte) 1);
            ++y1;
        }
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "NumericCastThatLosesPrecision" })
    private void genTreeLayer(World world, BlockPos pos, float size, byte index)
    {
        final int var7 = (int) (size + 0.618D);
        final byte var8 = otherCoordPairs[index];
        final byte var9 = otherCoordPairs[index + 3];
        final int[] var10 = { pos.getX(), pos.getY(), pos.getZ() };
        final int[] var11 = { 0, 0, 0 };
        int var12 = -var7;

        var11[index] = var10[index];
        while (var12 <= var7)
        {
            var11[var8] = var10[var8] + var12;
            int var13 = -var7;

            while (var13 <= var7)
            {
                final double var15 =
                        StrictMath.pow(Math.abs(var12) + 0.5D, 2.0D) + StrictMath.pow(Math.abs(var13) + 0.5D, 2.0D);

                if (var15 > size * size) ++var13;
                else
                {
                    var11[var9] = var10[var9] + var13;
                    final Block block = world.getBlockState(new BlockPos(var11[0], var11[1], var11[2])).getBlock();

                    if (block != null && block.isLeaves(world, new BlockPos(var11[0], var11[1], var11[2]))) ++var13;
                    else
                    {
                        placeLeaves(world, new BlockPos(var11[0], var11[1], var11[2]));
                        ++var13;
                    }
                }
            }
            ++var12;
        }
    }

    @SuppressWarnings({ "MethodWithMultipleLoops", "OverlyLongMethod", "NumericCastThatLosesPrecision" })
    private int generateLeafNodeList(World world)
    {
        int height = (int) (heightLimit * HEIGHT_ATTENUATION);

        if (height >= heightLimit) height = heightLimit - 1;

        int leavesPortion = (int) (1.382D + StrictMath.pow(LEAF_DENSITY * heightLimit / 13.0D, 2.0D));

        if (leavesPortion < 1) leavesPortion = 1;

        final int[][] nodeList = new int[leavesPortion * heightLimit][4];
        int leafLimit = basePos[1] + heightLimit - LEAF_DISTANCE_LIMIT;
        final int trunkTopY = basePos[1] + height;
        int canopyHeight = leafLimit - basePos[1];
        nodeList[0][0] = basePos[0];
        nodeList[0][1] = leafLimit;
        nodeList[0][2] = basePos[2];
        nodeList[0][3] = trunkTopY;
        --leafLimit;

        int var4 = 1;
        while (canopyHeight >= 0)
        {
            final float var8 = layerSize(canopyHeight);

            if (var8 < 0.0F)
            {
                --leafLimit;
                --canopyHeight;
            } else
            {
                int var7 = 0;
                final double var9 = 0.5D;
                while (var7 < leavesPortion)
                {
                    final double var11 = SCALE_WIDTH * var8 * (rng.nextFloat() + 0.328D);
                    final double var13 = rng.nextFloat() * 2.0D * Math.PI;
                    final int var15 = MathHelper.floor_double(var11 * StrictMath.sin(var13) + basePos[0] + var9);
                    final int var16 = MathHelper.floor_double(var11 * StrictMath.cos(var13) + basePos[2] + var9);
                    final int[] var17 = { var15, leafLimit, var16 };
                    final int[] var18 = { var15, leafLimit + LEAF_DISTANCE_LIMIT, var16 };

                    if (checkBlockLine(world, var17, var18) == -1)
                    {
                        final int[] var19 = { basePos[0], basePos[1], basePos[2] };
                        final double var20 = Math.sqrt(StrictMath.pow(Math.abs(basePos[0] - var17[0]), 2.0D) +
                                StrictMath.pow(Math.abs(basePos[2] - var17[2]), 2.0D));
                        final double var22 = var20 * BRANCH_SLOPE;

                        var19[1] = var17[1] - var22 > trunkTopY ? trunkTopY : (int) (var17[1] - var22);

                        if (checkBlockLine(world, var19, var17) == -1)
                        {
                            nodeList[var4][0] = var15;
                            nodeList[var4][1] = leafLimit;
                            nodeList[var4][2] = var16;
                            nodeList[var4][3] = var19[1];
                            ++var4;
                        }
                    }
                    ++var7;
                }

                --leafLimit;
                --canopyHeight;
            }
        }

        leafNodes = new int[var4][4];
        System.arraycopy(nodeList, 0, leafNodes, 0, var4);

        return height;
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    private float layerSize(int level)
    {
        if (level < heightLimit * 0.3D) return -1.618F;

        final float maxSize = heightLimit / 2.0F;
        final float height = heightLimit / 2.0F - level;
        float size;

        if (height == 0.0F) size = maxSize;
        else size = Math.abs(height) >= maxSize ? 0.0F :
                (float) Math.sqrt(StrictMath.pow(Math.abs(maxSize), 2.0D) - StrictMath.pow(Math.abs(height), 2.0D));

        size *= 0.5F;
        return size;
    }
}
