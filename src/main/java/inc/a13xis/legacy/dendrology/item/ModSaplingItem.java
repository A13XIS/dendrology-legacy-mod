package inc.a13xis.legacy.dendrology.item;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.block.ModSaplingBlock;
import inc.a13xis.legacy.koresample.tree.item.SaplingItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ModSaplingItem extends SaplingItem
{
    private final ModSaplingBlock sapling;

    public ModSaplingItem(Block block, ModSaplingBlock sapling, String[] names)
    {
        super(block, sapling, names);
        this.sapling = sapling;
    }

    @Override
    public String getPotionEffect(ItemStack itemStack)
    {
        return sapling.getPotionEffect(itemStack);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("sapling", sapling).toString();
    }
}
