package inc.a13xis.legacy.koresample;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;

@Mod(modid = TheMod.MOD_ID, name = TheMod.MOD_NAME, version = TheMod.MOD_VERSION, useMetadata = true)
public final class TheMod {
	public static final String MOD_ID = "koresample";
	public static final String MOD_NAME = "Kore Sample";
	public static final String MOD_VERSION = "1.12-L1";
	private static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ':';

	@Instance(MOD_ID)
	public static TheMod INSTANCE;

	public static String resourcePrefix() {
		return RESOURCE_PREFIX;
	}
}
