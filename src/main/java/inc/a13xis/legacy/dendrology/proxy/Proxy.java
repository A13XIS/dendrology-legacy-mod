package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.proxy.render.RenderProxy;
import net.minecraftforge.fml.common.SidedProxy;

@SuppressWarnings({ "StaticNonFinalField", "PublicField" })
public enum Proxy
{
    ;
    private static final String CLIENT_RENDER_PROXY_CLASS ="inc.a13xis.legacy.dendrology.proxy.render.ClientRenderProxy";
    private static final String RENDER_PROXY_CLASS = "inc.a13xis.legacy.dendrology.proxy.render.RenderProxy";
    private static final String CLIENT_COMMON_PROXY_CLASS ="inc.a13xis.legacy.dendrology.proxy.ClientCommonProxy";
    private static final String COMMON_PROXY_CLASS = "inc.a13xis.legacy.dendrology.proxy.CommonProxy";

    @SidedProxy(clientSide = CLIENT_RENDER_PROXY_CLASS, serverSide = RENDER_PROXY_CLASS)
    public static RenderProxy render;

    @SidedProxy(clientSide = CLIENT_COMMON_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy common;
}
