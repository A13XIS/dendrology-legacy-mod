package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.compat.chisel.ChiselWoodBlock;
import inc.a13xis.legacy.dendrology.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientCommonProxy extends CommonProxy {

    @Override
    public void registerRenders(){
        ModItems.registerAllItemRenders();
        ModBlocks.registerAllBlockRenders();
    }

    @Override
    public void onItemRightClick(ItemStack content, World world, EntityPlayer player) {


            final String message;
            if (content == null)
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.empty"): net.minecraft.client.resources.I18n.format(TheMod.MOD_ID+":parcel.empty");
            else
            {
                final String itemName = net.minecraft.client.resources.I18n.format(content.getItem().getUnlocalizedName(content) + ".name");
                message = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,TheMod.MOD_ID+":parcel.full",itemName):net.minecraft.client.resources.I18n.format(TheMod.MOD_ID+":parcel.full");

            }

            player.sendMessage(new TextComponentString(message));
    }

    @Override
    public String safeTranslate(String settingName) {
        return TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,settingName): net.minecraft.client.resources.I18n.format(settingName);
    }

    @Override
    public void registerIntegratorRenders(String mod, ArrayList<ItemBlock> itemBlocks){
        switch (mod){
            case "chisel":
                for(ItemBlock blockItem:itemBlocks){
                     for(ChiselWoodBlock.VARIATIONS v: ChiselWoodBlock.VARIATIONS.values()){
                        ModelResourceLocation typeLocation = new ModelResourceLocation(blockItem.getBlock().getRegistryName(),"variation="+v.name());
                        ModelLoader.setCustomModelResourceLocation(blockItem,v.ordinal(),typeLocation);
                    }
                }
        }
    }
}
