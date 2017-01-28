package inc.a13xis.legacy.dendrology.item;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.block.ModSapling2Block;
import inc.a13xis.legacy.dendrology.block.ModSaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.item.SaplingItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ModSaplingItem extends SaplingItem
{
    private final SaplingBlock sapling;

    public ModSaplingItem(Block block, ModSaplingBlock sapling, String[] names)
    {
        super(block, sapling, names);
        this.sapling = sapling;
    }

    public ModSaplingItem(Block block, ModSapling2Block sapling, String[] names)
    {
        super(block, sapling, names);
        this.sapling = sapling;
    }

    @Override
    public String getPotionEffect(ItemStack itemStack)
    {
        return sapling instanceof ModSaplingBlock?((ModSaplingBlock)sapling).getPotionEffect(itemStack):((ModSapling2Block)sapling).getPotionEffect(itemStack);
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("sapling", sapling).toString();
    }
}
