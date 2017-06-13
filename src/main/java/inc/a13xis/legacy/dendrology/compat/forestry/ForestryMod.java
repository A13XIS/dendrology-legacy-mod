package inc.a13xis.legacy.dendrology.compat.forestry;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies;
import inc.a13xis.legacy.koresample.compat.Integrator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import forestry.api.core.ForestryAPI;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public final class ForestryMod extends Integrator
{
    private static final String MOD_ID = "Forestry";
    private static final String MOD_NAME = MOD_ID;

    private static void addBackpackItem(String backpack, ItemStack stack)
    {
        if (stack == null) return;

        addBackpackItem(backpack, stack.getItem(), stack.getItemDamage());
    }

    private static void addBackpackItem(String backpack, Item item, int damage)
    {
        sendBackpackMessage(String.format("%s@%s:%d", backpack,  item.getRegistryName(), damage));
    }

    private static void addBackpackItems()
    {
        TheMod.logger().info("Extending Forestry's backpacks.");
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
        {
            final ItemStack sapling = new ItemStack(tree.saplingBlock(), 1, tree.saplingSubBlockVariant().ordinal());
            addBackpackItem("forester", sapling);

            final ItemStack log = new ItemStack(tree.logBlock(), 1, tree.logSubBlockVariant().ordinal());
            addBackpackItem("forester", log);

            final ItemStack leaves = new ItemStack(tree.leavesBlock(), 1, tree.leavesSubBlockVariant().ordinal());
            addBackpackItem("forester", leaves);

            final ItemStack stairs = new ItemStack(tree.stairsBlock());
            addBackpackItem("builder", stairs);

            final ItemStack slabs = new ItemStack(tree.singleSlabBlock());
            addBackpackItem("builder", slabs);

            final ItemStack planks = new ItemStack(tree.woodBlock());
            addBackpackItem("builder", slabs);

            final ItemStack door = new ItemStack(tree.doorBlock());
            addBackpackItem("builder", door);
        }
    }

    private static void addFarmables()
    {
        TheMod.logger().info("Adding farmable saplings to Forestry's farms.");
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
        {
            //noinspection ObjectAllocationInLoop
            final ItemStack sapling = new ItemStack(tree.saplingBlock(), 1, tree.saplingSubBlockVariant().ordinal());
            addFarmableSapling(sapling);
        }
    }

    private static void addFarmableSapling(ItemStack stack)
    {
        if (stack == null) return;

        addFarmableSapling(stack.getItem(), stack.getItemDamage());
    }

    private static void addFarmableSapling(Item item, int damage)
    {
        sendFarmableMessage(String.format("farmArboreal@%s:%d", item.getRegistryName(), damage));
    }

    @Method(modid = MOD_ID)
    private static void addSaplingRecipes()
    {
        TheMod.logger().info("Adding sapling recipes to Forestry's fermenter.");

        final int fermentationValue = ForestryAPI.activeMode.getIntegerSetting("fermenter.yield.sapling");
        for (final OverworldTreeSpecies tree : OverworldTreeSpecies.values())
        {
            //noinspection ObjectAllocationInLoop
            final ItemStack sapling = new ItemStack(tree.saplingBlock(), 1, tree.saplingSubBlockVariant().ordinal());
            RecipeManagers.fermenterManager
                    .addRecipe(sapling, fermentationValue, 1.0f, fluidStack("biomass"), fluidStack("water"));
            RecipeManagers.fermenterManager
                    .addRecipe(sapling, fermentationValue, 1.5f, fluidStack("biomass"), fluidStack("juice"));
            RecipeManagers.fermenterManager
                    .addRecipe(sapling, fermentationValue, 1.5f, fluidStack("biomass"), fluidStack("honey"));
        }
    }

    private static void sendBackpackMessage(String message)
    {
        FMLInterModComms.sendMessage("Forestry", "add-backpack-items", message);
    }

    private static void sendFarmableMessage(String message)
    {
        FMLInterModComms.sendMessage("Forestry", "add-farmable-sapling", message);
    }

    private static FluidStack fluidStack(String fluidName) { return FluidRegistry.getFluidStack(fluidName, 1000); }

    private static void init() { addFarmables(); }

    @Method(modid = MOD_ID)
    private static void postInit()
    {
        addBackpackItems();
        addSaplingRecipes();
    }

    @Override
    public void doIntegration(ModState modState)
    {
        if (Loader.isModLoaded(MOD_ID) && Settings.INSTANCE.integrateForestry())
        {
            switch (modState)
            {
                case INITIALIZED:
                    init();
                    break;
                case POSTINITIALIZED:
                    postInit();
                    break;
                default:
            }
        }
    }

    @Override
    protected String modID() { return MOD_ID; }

    @Override
    protected String modName() { return MOD_NAME; }
}
