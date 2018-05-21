package inc.a13xis.legacy.koresample.common.util.event;

import net.minecraftforge.fml.common.eventhandler.EventBus;

public interface ForgeEventListener {
	public default void listen(EventBus eventBus) {
		eventBus.register(this);
	}
}
