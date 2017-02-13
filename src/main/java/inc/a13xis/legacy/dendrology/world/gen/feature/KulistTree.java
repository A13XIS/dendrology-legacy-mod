package inc.a13xis.legacy.dendrology.world.gen.feature;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.world.gen.feature.kulist.LargeKulistTree;
import inc.a13xis.legacy.dendrology.world.gen.feature.kulist.NormalKulistTree;
import inc.a13xis.legacy.koresample.tree.DefinesTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class KulistTree extends AbstractTree
{
    private final AbstractTree treeGen;
    private final AbstractTree largeTreeGen;

    public KulistTree(boolean fromSapling)
    {
        super(fromSapling);
        treeGen = new NormalKulistTree(fromSapling);
        largeTreeGen = new LargeKulistTree(fromSapling);
    }

    public KulistTree() { this(true); }

    @Override
    public void setTree(DefinesTree tree)
    {
        treeGen.setTree(tree);
        largeTreeGen.setTree(tree);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("treeGen", treeGen).add("largeTreeGen", largeTreeGen).toString();
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (rand.nextInt(10) < 9) return treeGen.generate(world, rand, pos);

        return largeTreeGen.generate(world, rand, pos);
    }

}
