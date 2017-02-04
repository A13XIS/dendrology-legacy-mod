package inc.a13xis.legacy.dendrology.content.crafting;

import inc.a13xis.legacy.dendrology.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

@SuppressWarnings("MethodMayBeStatic")
public class Smelter
{
    public void registerSmeltings()
    {
        for (final Block log : ModBlocks.logBlocks())
        {
            registerCharcoalSmelting(log);
        }
    }

    private static void registerCharcoalSmelting(Block log)
    {
        FurnaceRecipes.instance().addSmelting(Item.getItemFromBlock(log), new ItemStack(Items.coal, 1, 1), 0.15F);
    }
}
