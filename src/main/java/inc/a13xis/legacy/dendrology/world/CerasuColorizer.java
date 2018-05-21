package inc.a13xis.legacy.dendrology.world;

import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public enum CerasuColorizer implements IResourceManagerReloadListener {
	INSTANCE;
	@SuppressWarnings("StaticNonFinalField")
	private static int[] buffer = new int[256 * 256];

	public static int getInventoryColor() {
		return buffer[0x80 << 8 | 0x80];
	}

	public static int getColor(BlockPos pos) {
		final int i = pos.getX() + pos.getY() & 0xff;
		final int j = pos.getZ() + pos.getY() & 0xff;
		return buffer[i << 8 | j];
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		try {
			//noinspection AssignmentToStaticFieldFromInstanceMethod
			buffer = TextureUtil.readImageData(resourceManager,
					new ResourceLocation(TheMod.MOD_ID, "textures/colormap/cerasu.png"));
		} catch (final IOException ignored) {
		}
	}
}
