package inc.a13xis.legacy.dendrology.config.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

@SuppressWarnings({ "WeakerAccess", "UnusedDeclaration" })
public final class ModGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance) { }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigGUI.class; }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { return null; }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { return null; }
}
