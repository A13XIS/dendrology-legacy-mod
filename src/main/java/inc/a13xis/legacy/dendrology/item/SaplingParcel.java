package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.content.ParcelManager;
import inc.a13xis.legacy.dendrology.proxy.Proxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class SaplingParcel extends Item
{
    public SaplingParcel()
    {
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setUnlocalizedName("parcel");
        setRegistryName("parcel");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> information, boolean unused)
    {
        String test = String.format("%s%s", TheMod.getResourcePrefix(), "parcel.tooltip");
        String text = TheMod.fallBackExsists()?TheMod.getFallBack().formatAndSafeTranslate(null,test):net.minecraft.client.resources.I18n.format(test);
        information.add(text);
    }

    private static String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", TheMod.getResourcePrefix(),
                getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote)
        {
            final ItemStack content = ParcelManager.INSTANCE.randomItem();

            if (content != null)
            {
                content.stackSize=1;
                final EntityItem entityItem = player.dropItem(content,true,true);
                if (entityItem != null)
                {
                    entityItem.setPickupDelay(0);
                    entityItem.setOwner(player.getCommandSenderEntity().getName());
                }
            }

            itemStack.stackSize--;
            Proxy.common.onItemRightClick(content,world,player);
        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,itemStack);
    }

    @Override
    public String getUnlocalizedName(ItemStack unused) { return getUnlocalizedName(); }

    @SideOnly(Side.CLIENT)
    public final void registerItemModel()
    {
        ModelResourceLocation rloc = new ModelResourceLocation(getRegistryName(),"inventory");
        ModelLoader.setCustomModelResourceLocation(this, 0, rloc);
    }

}
