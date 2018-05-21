package inc.a13xis.legacy.koresample.config;

import net.minecraftforge.common.config.Configuration;

public interface ConfigSyncable {
	void convertOldConfig(Configuration oldConfig);

	void syncConfig(Configuration config);
}
