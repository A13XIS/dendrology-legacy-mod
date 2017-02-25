package inc.a13xis.legacy.dendrology.content;

import inc.a13xis.legacy.dendrology.block.ModSaplingBlock;
import inc.a13xis.legacy.dendrology.block.ModSlabBlock;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.datafix.fixes.PotionItems;
import net.minecraftforge.common.brewing.IBrewingRecipe;

/**
 * Created by Lexis on 16.02.2017.
 */
public class PotionBrewingRecipe implements IBrewingRecipe{

    private PotionType input;
    private ItemStack ingredient;
    private PotionType output;

    public PotionBrewingRecipe(PotionType input, ItemStack ingredient, PotionType output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input2) {
        Item test = input2.getItem();
        boolean test2 = (PotionUtils.getPotionFromItem(input2).equals(input));
        return input2.getItem() instanceof ItemPotion && (PotionUtils.getPotionFromItem(input2).equals(input));
    }

    @Override
    public boolean isIngredient(ItemStack ingredient2) {
        boolean test = ingredient2.getItem()==ingredient.getItem();
        boolean test2 = ingredient2.getItemDamage()==ingredient.getItemDamage();
        return ingredient2.getItem()==ingredient.getItem() && ingredient2.getItemDamage()==ingredient.getItemDamage();
    }

    @Override
    public ItemStack getOutput(ItemStack input2, ItemStack ingredient2) {
        boolean test = PotionUtils.getPotionFromItem(input2)==input;
        boolean test2 = ingredient2.getItemDamage()==ingredient.getItemDamage();
        if(PotionUtils.getPotionFromItem(input2)==input&&ingredient2.getItemDamage()==ingredient.getItemDamage()){
            return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM,1,0),output);
        }
        else{
            return null;
        }
    }
}
