package inc.a13xis.legacy.dendrology.config.client;

import com.google.common.collect.Lists;
import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public final class ConfigGUI extends GuiConfig {
	public ConfigGUI(GuiScreen parent) {
		super(parent, getConfigElements(), TheMod.MOD_ID, false, false,
				GuiConfig.getAbridgedConfigPath(TheMod.INSTANCE.configuration().toString()));
	}

	@SuppressWarnings("unchecked")
	private static List<IConfigElement> getConfigElements() {
		final List<IConfigElement> configElements = Lists.newArrayList();

		final Configuration config = TheMod.INSTANCE.configuration();
		final ConfigElement general = new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL));
		configElements.addAll(general.getChildElements());

		return configElements;
	}
}
