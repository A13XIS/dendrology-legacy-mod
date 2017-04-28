package inc.a13xis.legacy.dendrology.block;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.tree.DefinesDoor;
import net.minecraft.block.state.IBlockState;

public final class ModDoorBlock extends DoorBlock
{
    public ModDoorBlock(DefinesDoor definition)
    {
        super(definition);
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    protected String resourcePrefix()
    {
        return TheMod.getResourcePrefix();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
