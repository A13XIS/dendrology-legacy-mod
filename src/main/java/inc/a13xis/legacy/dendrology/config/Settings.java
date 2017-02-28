package inc.a13xis.legacy.dendrology.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.proxy.Proxy;
import inc.a13xis.legacy.koresample.config.ConfigSyncable;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;


import java.util.Map;

import static net.minecraft.world.storage.loot.LootTableList.*;


public enum Settings implements ConfigSyncable
{
    INSTANCE;
    public static final String CONFIG_VERSION = "3";
    private static final int MAX_OVERWORLD_TREE_GEN_RARITY = 10000;
    private static final int DEFAULT_OVER_WORLD_TREE_GEN_RARITY = 5;
    private static final ImmutableMap<ResourceLocation, Integer> DEFAULT_CHEST_RARITIES =
            ImmutableMap.copyOf(defaultChestRarities());
    private static final ImmutableMap<ResourceLocation, String> CHEST_CONFIG_NAMES = chestConfigNames();
    private final Map<ResourceLocation, Integer> chestRarities = defaultChestRarities();

    private int overworldTreeGenRarity = DEFAULT_OVER_WORLD_TREE_GEN_RARITY;
    private int saplingDropRarity = 200;
    private boolean integrateChisel = true;
    private boolean integrateForestry = true;
    private boolean integrateGardenStuff = true;
    private boolean integrateMFR = true;
    private boolean integrateMinechem = true;
    private boolean integrateStorageDrawers = true;

    private static ImmutableMap<ResourceLocation, String> chestConfigNames()
    {
        final Map<ResourceLocation, String> map = Maps.newHashMap();
        map.put(CHESTS_VILLAGE_BLACKSMITH, "blacksmithRarity");
        map.put(CHESTS_SPAWN_BONUS_CHEST, "bonusChestRarity");
        map.put(CHESTS_DESERT_PYRAMID, "desertTempleRarity");
        map.put(CHESTS_SIMPLE_DUNGEON, "dungeonRarity");
        map.put(CHESTS_JUNGLE_TEMPLE, "jungleTempleRarity");
        map.put(CHESTS_JUNGLE_TEMPLE_DISPENSER, "jungleTempleDispenserRarity");
        map.put(CHESTS_ABANDONED_MINESHAFT, "mineshaftCorridorRarity");
        map.put(CHESTS_STRONGHOLD_CORRIDOR, "strongholdCorridorRarity");
        map.put(CHESTS_STRONGHOLD_CROSSING, "strongholdCrossingRarity");
        map.put(CHESTS_STRONGHOLD_LIBRARY, "strongholdLibraryRarity");
        map.put(CHESTS_END_CITY_TREASURE, "endTreasureRarity");
        map.put(CHESTS_NETHER_BRIDGE, "netherBridgeRarity");
        map.put(CHESTS_IGLOO_CHEST, "iglooRarity");
        return ImmutableMap.copyOf(map);
    }

    //Rarities are percentages now!
    private static Map<ResourceLocation, Integer> defaultChestRarities()
    {
        final Map<ResourceLocation, Integer> map = Maps.newHashMap();
        map.put(CHESTS_VILLAGE_BLACKSMITH, 0);
        map.put(CHESTS_SPAWN_BONUS_CHEST, 0);
        map.put(CHESTS_DESERT_PYRAMID, 1);
        map.put(CHESTS_SIMPLE_DUNGEON, 1);
        map.put(CHESTS_JUNGLE_TEMPLE, 1);
        map.put(CHESTS_JUNGLE_TEMPLE_DISPENSER, 0);
        map.put(CHESTS_ABANDONED_MINESHAFT, 1);
        map.put(CHESTS_STRONGHOLD_CORRIDOR, 1);
        map.put(CHESTS_STRONGHOLD_CROSSING, 1);
        map.put(CHESTS_STRONGHOLD_LIBRARY, 1);
        map.put(CHESTS_END_CITY_TREASURE, 5);
        map.put(CHESTS_NETHER_BRIDGE, 5);
        map.put(CHESTS_IGLOO_CHEST, 1);
        return map;
    }

    private static int get(Configuration config, String settingName, int defaultValue)
    {
        return get(config, settingName, defaultValue, 0, Integer.MAX_VALUE);
    }

    private static int get(Configuration config, String settingName, String category, int defaultValue)
    {
        return get(config, settingName, category, defaultValue, 0, Integer.MAX_VALUE);
    }

    private static boolean get(Configuration config, String settingName, String category, boolean defaultValue)
    {
        return config.getBoolean(settingName, category, defaultValue, getLocalizedComment(settingName));
    }

    private static int get(Configuration config, String settingName, int defaultValue, int minumumValue,
                           int maximumValue)
    {
        return get(config, settingName, Configuration.CATEGORY_GENERAL, defaultValue, minumumValue, maximumValue);
    }

    private static int get(Configuration config, String settingName, String category, int defaultValue,
                           int minumumValue, int maximumValue)
    {
        return config.getInt(settingName, category, defaultValue, minumumValue, maximumValue,
                getLocalizedComment(settingName));

    }

    private static String getLocalizedComment(String settingName)
    {
        return Proxy.common.safeTranslate(settingName);
    }

    public static Iterable<ResourceLocation> chestTypes()
    {
        return DEFAULT_CHEST_RARITIES.keySet();
    }

    private void loadChestRarity(Configuration config, String category, ResourceLocation chestType)
    {
        final int defaultRarity = DEFAULT_CHEST_RARITIES.get(chestType);
        final String settingName = CHEST_CONFIG_NAMES.get(chestType);
        final int rarity = get(config, settingName, category, defaultRarity);
        chestRarities.put(chestType, rarity);
    }

    public int chestRarity(ResourceLocation chestType)
    {
        final Integer rarity = chestRarities.get(chestType);
        return rarity == null ? 0 : rarity;
    }

    public boolean isOverworldTreeGenEnabled() { return overworldTreeGenRarity != 0; }

    public int overworldTreeGenRarity() { return MAX_OVERWORLD_TREE_GEN_RARITY - overworldTreeGenRarity + 1; }

    @Override
    public void convertOldConfig(Configuration oldConfig)
    {
        if ("1".equals(oldConfig.getLoadedConfigVersion()))
        {
            for (final ResourceLocation chestType : DEFAULT_CHEST_RARITIES.keySet())
            {
                loadChestRarity(oldConfig, Configuration.CATEGORY_GENERAL, chestType);
            }

            overworldTreeGenRarity = get(oldConfig, "overworldTreeGenRarity", DEFAULT_OVER_WORLD_TREE_GEN_RARITY, 0,
                    MAX_OVERWORLD_TREE_GEN_RARITY);
        }

        syncConfig(TheMod.INSTANCE.configuration());
    }

    @Override
    public void syncConfig(Configuration config)
    {
        saplingDropRarity = get(config, "saplingDropRarity", Configuration.CATEGORY_GENERAL, saplingDropRarity);

        final String chestsCategory = Configuration.CATEGORY_GENERAL + ".chests";
        for (final ResourceLocation chestType : chestRarities.keySet())
        {
            loadChestRarity(config, chestsCategory, chestType);
        }

        final String worldGenCategory = Configuration.CATEGORY_GENERAL + ".worldgen";
        overworldTreeGenRarity =
                get(config, "overworldTreeGenRarity", worldGenCategory, DEFAULT_OVER_WORLD_TREE_GEN_RARITY, 0,
                        MAX_OVERWORLD_TREE_GEN_RARITY);

        final String integrationCategory = Configuration.CATEGORY_GENERAL + ".integration";
        integrateChisel = get(config, "integrateChisel", integrationCategory, true);
        integrateForestry = get(config, "integrateForestry", integrationCategory, true);
        integrateGardenStuff = get(config, "integrateGardenStuff", integrationCategory, true);
        integrateMFR = get(config, "integrateMFR", integrationCategory, true);
        integrateMinechem = get(config, "integrateMinechem", integrationCategory, true);
        integrateStorageDrawers = get(config, "integrateStorageDrawers", integrationCategory, true);
    }

    public int saplingDropRarity()
    {
        return saplingDropRarity;
    }

    public boolean integrateChisel() { return integrateChisel; }

    public boolean integrateForestry() { return integrateForestry; }

    public boolean integrateGardenStuff() { return integrateGardenStuff; }

    public boolean integrateMFR() { return integrateMFR; }

    public boolean integrateMinechem() { return integrateMinechem; }

    public boolean integrateStorageDrawers() { return integrateStorageDrawers; }
}
