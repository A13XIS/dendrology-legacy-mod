package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.content.ProvidesPotionEffect;
import inc.a13xis.legacy.koresample.tree.DefinesSapling;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public final class ModSaplingBlock extends SaplingBlock
{
    public ModSaplingBlock(Iterable<? extends DefinesSapling> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @SuppressWarnings("ReturnOfNull")
    public String getPotionEffect(ItemStack itemStack)
    {
        final List<DefinesSapling> subBlocks = subBlocks();
        final int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= subBlocks.size()) return null;

        final DefinesSapling subBlock = subBlocks.get(itemDamage);
        return subBlock instanceof ProvidesPotionEffect ? ((ProvidesPotionEffect) subBlock).potionEffect() : null;
    }
}
