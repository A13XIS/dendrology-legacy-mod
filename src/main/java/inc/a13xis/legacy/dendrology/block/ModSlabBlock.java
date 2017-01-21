package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.tree.DefinesSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public final class ModSlabBlock extends SlabBlock
{
    public ModSlabBlock(boolean isDouble, Iterable<? extends DefinesSlab> subBlocks)
    {
        super(isDouble, ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    @Override
    public String getUnlocalizedName(int meta) {
        return subBlocks().get(meta & (Integer.MAX_VALUE-1)).slabName();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty getVariantProperty() {
        return null;
    }

    @Override
    public Object getVariant(ItemStack stack) {
        return null;
    }
}
