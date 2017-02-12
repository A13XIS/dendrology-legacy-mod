package inc.a13xis.legacy.dendrology.world.gen.feature.acemus;

import inc.a13xis.legacy.dendrology.world.gen.feature.vanilla.AbstractLargeVanillaTree;

import static inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies.ACEMUS;

public class LargeAcemusTree extends AbstractLargeVanillaTree
{
    public LargeAcemusTree(boolean fromSapling) { super(fromSapling); }

    @Override
    protected int getUnmaskedLogMeta() { return ACEMUS.logSubBlockVariant().ordinal(); }
}
