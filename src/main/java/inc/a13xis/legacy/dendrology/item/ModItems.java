package inc.a13xis.legacy.dendrology.item;

import inc.a13xis.legacy.dendrology.TheMod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ObjectHolder(TheMod.MOD_ID)
public final class ModItems
{
    private static final SaplingParcel parcel = new SaplingParcel();

    @SideOnly(Side.CLIENT)
    public static void registerAllItemRenders(){
        parcel.registerItemModel();
    }

    public static SaplingParcel parcelInstance(){
        return parcel;
    }

}
