package inc.a13xis.legacy.dendrology.proxy.render;

import inc.a13xis.legacy.koresample.tree.DefinesLeaves;

public class RenderProxy
{
    Iterable<? extends DefinesLeaves> subblocks;
    public void init(Iterable<? extends DefinesLeaves> subblocks){
        this.subblocks=subblocks;
    }

    public void postInit() { }
}
