package inc.a13xis.legacy.koresample.tree;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ProvidesColor {
	@SideOnly(Side.CLIENT)
	int getLeavesInventoryColor();

	@SideOnly(Side.CLIENT)
	int getLeavesColor(IBlockAccess blockAccess, BlockPos pos);
}
