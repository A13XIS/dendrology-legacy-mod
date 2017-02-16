package inc.a13xis.legacy.dendrology.events;

import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.item.ModItems;
import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import inc.a13xis.legacy.koresample.common.block.StairsBlock;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import inc.a13xis.legacy.koresample.tree.block.LogBlock;
import inc.a13xis.legacy.koresample.tree.block.SaplingBlock;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Lexis on 16.02.2017.
 */
public class ModelLoadEvent {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        for (LogBlock logBlock : ModBlocks.logBlocks()) logBlock.registerBlockModels();
        for (WoodBlock woodBlock : ModBlocks.woodBlocks()) woodBlock.registerBlockModels();
        for (SlabBlock singleSlabBlock : ModBlocks.singleSlabBlocks()) singleSlabBlock.registerBlockModels();
        for (StairsBlock stairsBlock : ModBlocks.stairsBlocks()) stairsBlock.registerBlockModel();
        for (SaplingBlock saplingBlock : ModBlocks.saplingBlocks()) saplingBlock.registerBlockModels();
        for (LeavesBlock leavesBlock : ModBlocks.leavesBlocks()) leavesBlock.registerBlockModels();
        ModItems.parcelInstance().registerItemModel();
    }
}
