package inc.a13xis.legacy.dendrology.compat.chisel;

import inc.a13xis.legacy.dendrology.proxy.Proxy;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.chisel.api.carving.CarvingUtils;
import team.chisel.api.carving.ICarvingRegistry;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies;
import inc.a13xis.legacy.koresample.compat.Integrator;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public final class ChiselMod extends Integrator
{
    private static final String MOD_ID = "chisel";
    private static final String MOD_NAME = "Chisel 2";
    private static ArrayList<ChiselWoodSubBlockManager> mans=new ArrayList<>();
    private final CreativeTabs creativeTab = new CreativeTabs("chiselAncientTrees")
    {
        private final OverworldTreeSpecies ICON = OverworldTreeSpecies.PORFFOR;

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(ICON.woodBlock(), 1, ICON.woodSubBlockVariant().ordinal());
        }

        @SuppressWarnings("ReturnOfNull")
        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem() { return null; }
    };

    @Method(modid = MOD_ID)
    private void preInit()
    {
        loadBlocks();
    }

    private void assignAttributes(ChiselWoodBlock chiselWoodBlock)
    {
        OreDictionary.registerOre("plankWood", new ItemStack(chiselWoodBlock, 1, WILDCARD_VALUE));
        chiselWoodBlock.setCreativeTab(creativeTab);
        Blocks.FIRE.setFireInfo(chiselWoodBlock, 5, 20);
    }

    @Method(modid = MOD_ID)
    private static void finalizeVariationGroup(WoodBlock woodBlock, int subBlockIndex, String variationGroupName)
    {
        final ICarvingRegistry chisel = CarvingUtils.getChiselRegistry();
        chisel.addVariation(variationGroupName, woodBlock.getStateFromMeta(subBlockIndex), 0);
        chisel.setVariationSound(variationGroupName, MOD_ID + ":chisel.wood");
    }

    @Method(modid = MOD_ID)
    private void loadBlocks()
    {
        for (final OverworldTreeSpecies species : OverworldTreeSpecies.values())
        {
            final String speciesName = species.speciesName();
            final String variationGroupName = String.format("%s%s", speciesName, "_planks");
            final ChiselWoodSubBlockManager man = new ChiselWoodSubBlockManager(speciesName);
            mans.add(man);
            registerVariations(variationGroupName, man.getSpeciesBlock());
            finalizeVariationGroup(species.woodBlock(), species.woodSubBlockVariant().ordinal(), variationGroupName);
            assignAttributes(man.getSpeciesBlock());
        }
    }

    private static ChiselWoodBlock newChiselWoodBlock(String speciesName) {
        for(ChiselWoodSubBlockManager man:mans){
            if(man.getSpeciesBlock().getSpecies().equals(speciesName)) try {
                return man.getSpeciesBlock().cloneIt();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Method(modid = MOD_ID)
    private static void registerVariations(String variationGroupName, ChiselWoodBlock chiselWoodBlock)
    {
        for(ChiselWoodSubBlockManager man:mans){
            if(man.getSpeciesBlock().equals(chiselWoodBlock)){
                chiselWoodBlock.setRegistryName("chisel",variationGroupName);
                GameRegistry.register(chiselWoodBlock);
                GameRegistry.register(new ItemBlock(chiselWoodBlock).setRegistryName(chiselWoodBlock.getRegistryName()));
                Proxy.common.registerChiselRenders(man);
                return;
            }
        }
    }

    @Override
    public void doIntegration(ModState modState)
    {
        if (Loader.isModLoaded(MOD_ID)&& Settings.INSTANCE.integrateChisel())
        {
            switch (modState)
            {
                case PREINITIALIZED:
                    preInit();
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
