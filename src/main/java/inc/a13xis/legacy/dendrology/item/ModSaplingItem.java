package inc.a13xis.legacy.dendrology.item;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.block.ModSapling2Block;
import inc.a13xis.legacy.dendrology.block.ModSaplingBlock;
import inc.a13xis.legacy.dendrology.block.ModSlab2Block;
import inc.a13xis.legacy.dendrology.block.ModSlabBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.item.SaplingItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
    public String toString()
    {
        return Objects.toStringHelper(this).add("sapling", sapling).toString();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        Enum variant = sapling instanceof ModSaplingBlock? (Enum)sapling.getStateFromMeta(stack.getItemDamage()).getValue(ModSaplingBlock.VARIANT)
                :sapling instanceof ModSapling2Block? (Enum)sapling.getStateFromMeta(stack.getItemDamage()).getValue(ModSapling2Block.VARIANT)
                : null;
        return super.getUnlocalizedName()+"."+variant.name().toLowerCase();
    }
}
