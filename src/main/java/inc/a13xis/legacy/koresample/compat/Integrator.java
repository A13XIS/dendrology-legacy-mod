package inc.a13xis.legacy.koresample.compat;

import inc.a13xis.legacy.koresample.common.util.log.Logger;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState.ModState;

public abstract class Integrator implements Integrates {

	protected abstract void doIntegration(ModState modState);

	@Override
	public final void integrate(ModState modState) {
		if (Loader.isModLoaded(modID())) {
			doIntegration(modState);
		} else Logger.forMod(modID()).info("%s not present. %s state integration skipped.", modName(), modState);
	}

	protected abstract String modID();

	protected abstract String modName();
}
