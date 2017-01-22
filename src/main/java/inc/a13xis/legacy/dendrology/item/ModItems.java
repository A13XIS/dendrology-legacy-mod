package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(TheMod.MOD_ID)
public final class ModItems
{
    private static final SaplingParcel parcel = new SaplingParcel();

    @SuppressWarnings("MethodMayBeStatic")
    public void loadContent()
    {
        GameRegistry.registerItem(parcel, "parcel");

        addParcelToChests();
    }

    private static void addParcelToChests()
    {
        for (final String chestType : Settings.chestTypes())
            addParcelToChest(chestType);
    }

    private static void addParcelToChest(String chestType)
    {
        final int rarity = Settings.INSTANCE.chestRarity(chestType);
        if (rarity <= 0) return;

        final ItemStack parcelStack = new ItemStack(parcel);
        final WeightedRandomChestContent chestContent = new WeightedRandomChestContent(parcelStack, 1, 2, rarity);

        final ChestGenHooks chestGenInfo = ChestGenHooks.getInfo(chestType);
        chestGenInfo.addItem(chestContent);
    }

    public static void registerAllItemRenders(){
        parcel.registerModel();
    }
}
