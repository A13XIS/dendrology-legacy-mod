package inc.a13xis.legacy.dendrology.content.fuel;

import inc.a13xis.legacy.koresample.common.block.SlabBlock;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum FuelHandler implements IFuelHandler
{
    INSTANCE;

    public static void postInit()
    {
        GameRegistry.registerFuelHandler(INSTANCE);
    }

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        final Item fuelItem = fuel.getItem();
        final Material fuelMaterial = Block.getBlockFromItem(fuelItem).getMaterial();
        if (fuelMaterial.equals(Material.wood) && SlabBlock.isSingleSlab(fuelItem)) return 150;

        return 0;
    }
}
