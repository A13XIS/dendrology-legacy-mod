package inc.a13xis.legacy.koresample.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import inc.a13xis.legacy.koresample.common.util.log.Logger;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public final class ConfigEventHandler {
	private final File configFile;
	private final ConfigSyncable sync;
	private final Configuration configuration;
	private final String configVersion;
	private final String modID;
	private final Logger logger;

	public ConfigEventHandler(String modID, File configFile, ConfigSyncable sync, String configVersion) {
		this.modID = modID;
		logger = Logger.forMod(modID);
		this.configFile = configFile;
		this.sync = sync;

		final Configuration localConfiguration = new Configuration(configFile, configVersion);
		final Optional<Configuration> oldConfig;
		if (isConfigVersionMismatch(localConfiguration)) {
			backup(configFile);
			oldConfig = Optional.of(localConfiguration);
			configuration = new Configuration(configFile, configVersion);
		} else {
			oldConfig = Optional.absent();
			configuration = localConfiguration;
		}

		this.configVersion = configVersion;

		syncConfig(false, oldConfig);
	}

	private static String getModName(String modID) {
		final Map<String, ModContainer> mods = Loader.instance().getIndexedModList();
		final ModContainer mod = mods.get(modID);
		return mod == null ? "Unknown" : mod.getName();
	}

	private static boolean isConfigVersionMismatch(Configuration configuration) {
		return !configuration.getLoadedConfigVersion().equals(configuration.getDefinedConfigVersion());
	}

	public void activate() {
		FMLCommonHandler.instance().bus().register(this);
	}

	private void backup(File fileRef) {
		final File fileBak = new File(
				fileRef.getAbsolutePath() + '_' + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".old");
		logger.warning(
				"Your %s config file is out of date and could cause issues. The existing file will be renamed to %s and a new one will be generated.",
				getModName(modID), fileBak.getName());
		logger.warning(
				"%s will attempt to copy your old settings.",
				getModName(modID));

		final boolean success = fileRef.renameTo(fileBak);
		logger.warning("Rename %s successful.", success ? "was" : "was not");
	}

	public Configuration configuration() {
		return configuration;
	}

	private void loadConfig() {
		try {
			configuration.load();
		} catch (final RuntimeException e) {
			final File fileBak = new File(
					configFile.getAbsolutePath() + '_' + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) +
							".errored");
			logger.severe(
					"An exception occurred while loading your config file. This file will be renamed to %s and a new config file will be generated.",
					fileBak.getName());
			logger.severe("Exception encountered: %s", e.getLocalizedMessage());

			final boolean success = configFile.renameTo(fileBak);
			logger.warning("Rename %s successful.", success ? "was" : "was not");

			final Configuration newConfig = new Configuration(configFile, configVersion);
			final Set<String> categoryNames = configuration.getCategoryNames();
			newConfig.copyCategoryProps(configuration, categoryNames.toArray(new String[categoryNames.size()]));
			sync.syncConfig(newConfig);
			newConfig.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		String modid = event.getModID();
		if (modid.equalsIgnoreCase(modID)) {
			saveConfig();
			syncConfig();
		}
	}

	private void saveConfig() {
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	void syncConfig() {
		syncConfig(true, Optional.<Configuration>absent());
	}

	private void syncConfig(boolean doLoad, Optional<Configuration> oldConfig) {
		if (doLoad) loadConfig();

		sync.syncConfig(configuration);

		if (oldConfig.isPresent()) sync.convertOldConfig(oldConfig.get());

		saveConfig();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("configFile", configFile).add("sync", sync)
				.add("configuration", configuration).add("configVersion", configVersion).add("modID", modID)
				.add("logger", logger).toString();
	}
}
