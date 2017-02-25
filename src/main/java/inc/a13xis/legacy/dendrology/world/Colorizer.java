package inc.a13xis.legacy.dendrology.world;

import inc.a13xis.legacy.dendrology.block.ModLeaves2Block;
import inc.a13xis.legacy.dendrology.block.ModLeavesBlock;
import inc.a13xis.legacy.dendrology.block.ModLog2Block;
import inc.a13xis.legacy.dendrology.block.ModLogBlock;
import inc.a13xis.legacy.dendrology.item.ModLeavesItem;
import inc.a13xis.legacy.koresample.tree.block.LeavesBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class Colorizer {
    public static void registerBlockColor(final LeavesBlock block){
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor(){
            @Override
            public int colorMultiplier(IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex) {
                if(pos != null) {
                    if(block instanceof ModLeavesBlock){
                        String val = ((ModLogBlock.EnumType)access.getBlockState(pos).getValue(ModLeavesBlock.VARIANT)).name();
                        switch (val){
                            case "ACEMUS":
                                return AcemusColorizer.getColor(pos);
                            case "CERASU":
                                return CerasuColorizer.getColor(pos);
                        }
                    }
                    else if(block instanceof ModLeaves2Block){
                        String val = ((ModLog2Block.EnumType)access.getBlockState(pos).getValue(ModLeaves2Block.VARIANT)).name();
                        if (val.equals("KULIST")){
                            return KulistColorizer.getColor(pos);
                        }
                    }

                }
                return BiomeColorHelper.getFoliageColorAtPos(access,pos);
            }
        }, block);
    }

    public static void registerItemBlockColor(final LeavesBlock block){
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {

                if(block instanceof ModLeavesBlock){
                    String val = ((ModLogBlock.EnumType)((ModLeavesItem)stack.getItem()).getBlock().getStateFromMeta(stack.getItemDamage()).getValue(ModLeavesBlock.VARIANT)).name();
                    switch (val){
                       case "ACEMUS":
                            return AcemusColorizer.getInventoryColor();
                        case "CERASU":
                            return CerasuColorizer.getInventoryColor();

                    }
                }
                else if(block instanceof ModLeaves2Block){
                    String val = ((ModLog2Block.EnumType)((ModLeavesItem)stack.getItem()).getBlock().getStateFromMeta(stack.getItemDamage()).getValue(ModLeaves2Block.VARIANT)).name();
                    if(val.equals("KULIST")){
                        return KulistColorizer.getInventoryColor();
                    }
                }
                return ColorizerFoliage.getFoliageColorBasic();
            }
        },block);
    }
}
