package inc.a13xis.legacy.dendrology.compat.chisel;

import inc.a13xis.legacy.dendrology.block.ModWoodBlock;
import inc.a13xis.legacy.dendrology.proxy.Proxy;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import org.apache.commons.lang3.tuple.Pair;
import team.chisel.api.carving.CarvingUtils;
import team.chisel.api.carving.ICarvingRegistry;
import inc.a13xis.legacy.dendrology.config.Settings;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies;
import inc.a13xis.legacy.koresample.compat.Integrator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public final class ChiselMod extends Integrator
{
    private static final String MOD_ID = "chisel";
    private static final String MOD_NAME = "Chisel 2";

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
        public ItemStack getTabIconItem() { return null; }
    };

    @Method(modid = MOD_ID)
    private void preInit()
    {
        loadBlocks();
    }

    private void assignAttributes(ChiselWoodBlock chiselWoodBlock)
    {
        OreDictionary.registerOre("plankWood", new ItemStack(Item.getItemFromBlock(chiselWoodBlock), 1, WILDCARD_VALUE));
        chiselWoodBlock.setCreativeTab(creativeTab);
        Blocks.FIRE.setFireInfo(chiselWoodBlock, 5, 20);
    }

    @Method(modid = MOD_ID)
    private static void addVariationGroup(WoodBlock woodBlock, ChiselWoodBlock cwoodBlock, int subBlockIndex, String variationGroupName)
    {
        final ICarvingRegistry chisel = CarvingUtils.getChiselRegistry();
        for (ChiselWoodBlock.VARIATIONS v: ChiselWoodBlock.VARIATIONS.values()){
            if(v==ChiselWoodBlock.VARIATIONS.CLEAN)
                chisel.addVariation(variationGroupName,woodBlock.getDefaultState().withProperty(ModWoodBlock.VARIANT,Enum.valueOf(ModWoodBlock.EnumType.class,cwoodBlock.getSpeciesName().toUpperCase())),v.ordinal());
            else
                chisel.addVariation(variationGroupName,cwoodBlock.withVariation(v),v.ordinal());
        }
    }

    @Method(modid = MOD_ID)
    private void loadBlocks()
    {
        ArrayList<ItemBlock> itemList = new ArrayList<>();
        for (final OverworldTreeSpecies species : OverworldTreeSpecies.values())
        {
            final String speciesName = species.speciesName();
            final String variationGroupName = String.format("%s%s", "planks_", speciesName);
            final ChiselWoodBlock woodBlock = new ChiselWoodBlock(variationGroupName,speciesName);

            ItemBlock p = woodBlock.registerAndNoteBlock();
            itemList.add(p);
            addVariationGroup(species.woodBlock(),woodBlock,species.woodSubBlockVariant().ordinal(), variationGroupName);
            assignAttributes(woodBlock);
        }
        Proxy.common.registerIntegratorRenders(MOD_ID,itemList);
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
