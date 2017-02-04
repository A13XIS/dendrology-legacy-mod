package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.koresample.tree.DefinesTree;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static com.google.common.base.Preconditions.checkArgument;
public abstract class AbstractTree extends WorldGenAbstractTree
{
    protected static final ImmutableList<ImmutablePair<Integer, Integer>> BRANCH_DIRECTIONS = ImmutableList
            .of(ImmutablePair.of(-1, 0), ImmutablePair.of(1, 0), ImmutablePair.of(0, -1), ImmutablePair.of(0, 1),
                    ImmutablePair.of(-1, 1), ImmutablePair.of(-1, -1), ImmutablePair.of(1, 1), ImmutablePair.of(1, -1));
    private DefinesTree tree = null;

    protected AbstractTree(boolean fromSapling) { super(fromSapling); }

    @SuppressWarnings("WeakerAccess")
    protected boolean canBeReplacedByLog(World world, BlockPos pos)
    {
        final Block block = world.getBlockState(pos).getBlock();

        return block.isAir(world, pos) || block.isLeaves(world, pos);
    }

    public void setTree(DefinesTree tree)
    {
        this.tree = tree;
    }

    protected boolean isPoorGrowthConditions(World world, BlockPos pos, int height, IPlantable plantable)
    {
        checkArgument(height > 0);
        if (pos.getY() < 1 || pos.getY() + height + 1 > world.getHeight()) return true;
        if (!hasRoomToGrow(world, pos, height)) return true;

        final Block block = world.getBlockState(pos.down()).getBlock();
        return !block.canSustainPlant(world, pos.down(), EnumFacing.UP, plantable);
    }

    protected boolean hasRoomToGrow(World world, BlockPos pos, int height)
    {
        for (int y1 = pos.getY(); y1 <= pos.getY() + 1 + height; ++y1)
            if (!isReplaceable(world, new BlockPos(pos.getX(), y1, pos.getZ()))) return false;

        return true;
    }

    LeavesBlock getLeavesBlock() { return tree.leavesBlock(); }

    private int getLeavesMetadata() { return tree.leavesSubBlockVariant().ordinal(); }

    protected LogBlock getLogBlock() { return tree.logBlock(); }

    protected int getLogMetadata() { return tree.logSubBlockVariant().ordinal(); }

    protected SaplingBlock getSaplingBlock() {
        return tree.saplingBlock(); }

    protected void placeLeaves(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock().canBeReplacedByLeaves(world, pos))
            func_175905_a(world, pos, getLeavesBlock(), getLeavesMetadata());
    }

    protected void placeLog(World world, BlockPos pos)
    {
        if (canBeReplacedByLog(world, pos))
            func_175905_a(world, pos, getLogBlock(), getLogMetadata());
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("tree", tree).toString();
    }
}
