package inc.a13xis.legacy.dendrology.events;

import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.item.ModItems;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class GenerationEvents {

    /*@SubscribeEvent
    public void addAllSaplingsToChests(LootTableLoadEvent e)
    {
        final int rarity = Settings.INSTANCE.chestRarity(e.getName());
        if (rarity <= 0) return;
        LootTable table = e.getTable();
        ArrayList<LootEntry> entries = new ArrayList<LootEntry>();
        for(final DefinesSapling def:ModBlocks.taxonomyInstance().saplingDefinitions()) {
            if (def.saplingBlock() instanceof ModSaplingBlock)
                entries.add(new LootEntryItem(new ModSaplingItem(def.saplingBlock(), (ModSaplingBlock) def.saplingBlock(), new String[]{def.saplingSubBlockVariant().name().toLowerCase()}), rarity, 0, new LootFunction[]{
                        new LootFunction(new LootCondition[]{}) {
                            @Override
                            public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
                                stack.setItemDamage(def.saplingSubBlockVariant().ordinal());
                                return stack;
                            }
                        }
                }, new LootCondition[]{}, def.saplingBlock().getUnlocalizedName()));

            else
                entries.add(new LootEntryItem(new ModSaplingItem(def.saplingBlock(), (ModSapling2Block) def.saplingBlock(), new String[]{def.saplingSubBlockVariant().name().toLowerCase()}), rarity, 0, new LootFunction[]{
                        new LootFunction(new LootCondition[]{}) {
                            @Override
                            public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
                                stack.setItemDamage(def.saplingSubBlockVariant().ordinal());
                                return stack;
                            }
                        }
                }, new LootCondition[]{}, def.saplingBlock().getUnlocalizedName()));

        }
        table.addPool(new LootPool(entries.toArray(new LootEntry[entries.size()]),new LootCondition[]{new LootCondition() {
            @Override
            public boolean testCondition(Random rand, LootContext context) {
                return rarity>=100||rand.nextInt(100)+1<=rarity;
            }
        }},new RandomValueRange(0,1),new RandomValueRange(0),"dendrology:surprise"));
    }*/

    @SubscribeEvent
    public void addParcelToChest(LootTableLoadEvent e)
    {
        final int rarity = Settings.INSTANCE.chestRarity(e.getName());
        if (rarity <= 0) return;
        LootEntry[] entries = new LootEntry[]{new LootEntryItem(ModItems.parcelInstance(),rarity,rarity,new LootFunction[]{},new LootCondition[0],ModItems.parcelInstance().getUnlocalizedName())};
        e.getTable().addPool(new LootPool(entries,new LootCondition[]{new LootCondition() {
            @Override
            public boolean testCondition(Random rand, LootContext context) {
                 return rand.nextInt(2000000000)<=rarity;
            }
        }},new RandomValueRange(1),new RandomValueRange(1),"dendrology:gift"));
    }

}
