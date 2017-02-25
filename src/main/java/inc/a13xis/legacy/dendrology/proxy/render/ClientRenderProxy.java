package inc.a13xis.legacy.dendrology.proxy.render;

import inc.a13xis.legacy.dendrology.world.AcemusColorizer;
import inc.a13xis.legacy.dendrology.world.CerasuColorizer;
import inc.a13xis.legacy.dendrology.world.Colorizer;
import inc.a13xis.legacy.dendrology.world.KulistColorizer;
import inc.a13xis.legacy.koresample.tree.DefinesLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;

public final class ClientRenderProxy extends RenderProxy
{
    @Override
    public void postInit()
    {
        for(DefinesLeaves define:subblocks){
            Colorizer.registerBlockColor(define.leavesBlock());
            Colorizer.registerItemBlockColor(define.leavesBlock());
        }
        final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager)
        {
            // These casts are bad, but we don't have another way to do this.
            ((IReloadableResourceManager) resourceManager).registerReloadListener(AcemusColorizer.INSTANCE);
            ((IReloadableResourceManager) resourceManager).registerReloadListener(CerasuColorizer.INSTANCE);
            ((IReloadableResourceManager) resourceManager).registerReloadListener(KulistColorizer.INSTANCE);
        }
    }
}
