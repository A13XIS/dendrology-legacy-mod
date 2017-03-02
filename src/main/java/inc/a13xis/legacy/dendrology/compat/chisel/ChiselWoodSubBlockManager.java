package inc.a13xis.legacy.dendrology.compat.chisel;

import com.google.common.base.Objects;
import inc.a13xis.legacy.dendrology.content.overworld.OverworldTreeSpecies;
import inc.a13xis.legacy.koresample.common.util.multiblock.SubBlockManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import java.util.HashMap;
import java.util.List;

public final class ChiselWoodSubBlockManager implements SubBlockManager, Cloneable
{
    private final ChiselWoodBlock speciesBlock;

//            .of("clean", "short", "vertical", "vertical-uneven", "parquet", "fancy", "blinds", "panel-nails",
//                    "double-side", "crate", "crate-fancy", "crateex", "large", "chaotic-hor", "chaotic", "double-top");

        public ChiselWoodSubBlockManager(String speciesName)
    {
        this.speciesBlock = new ChiselWoodBlock(speciesName,this);
    }

    public void registerModels()
    {
       for(ChiselWoodBlock.EnumVars var:ChiselWoodBlock.EnumVars.values()){
           ModelResourceLocation typeLocation = new ModelResourceLocation(speciesBlock.getRegistryName(),"variation="+var.name().toLowerCase());
           Item blockItem = Item.getItemFromBlock(speciesBlock);
           ModelLoader.setCustomModelResourceLocation(blockItem,var.ordinal(),typeLocation);
       }
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs unused, List subBlocks)
    {
        final int numSubBlocks = ChiselWoodBlock.EnumVars.values().length - 1;
        for (int i = 0; i < numSubBlocks; i++)
            subBlocks.add(new ItemStack(item, 1, i));
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).add("speciesName", speciesBlock.getSpecies()).toString();
    }

    public ChiselWoodBlock getSpeciesBlock() {
        return speciesBlock;
    }
}
