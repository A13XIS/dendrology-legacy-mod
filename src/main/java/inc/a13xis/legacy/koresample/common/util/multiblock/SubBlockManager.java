package inc.a13xis.legacy.koresample.common.util.multiblock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface SubBlockManager {
	@SideOnly(Side.CLIENT)
	void registerModels();

	@SideOnly(Side.CLIENT)
	void getSubBlocks(Item item, CreativeTabs tabs, List list);
}
