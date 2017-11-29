package inc.a13xis.legacy.dendrology.events;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.item.ModItems;
import inc.a13xis.legacy.dendrology.proxy.Proxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TheMod.MOD_ID)
public class Registrator {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().register(ModItems.parcelInstance());
        ModBlocks.registerAllItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        ModBlocks.registerAllBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Proxy.common.registerRenders();
    }
}
