package inc.a13xis.legacy.koresample.common.util.log;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public final class Logger {
	private final org.apache.logging.log4j.Logger logger;

	private Logger(String modID) {
		logger = LogManager.getLogger(modID);
	}

	public static Logger forMod(String modID) {
		return new Logger(modID);
	}

	public void info(final String format, final Object... args) {
		log(Level.INFO, format, args);
	}

	public void log(final Level level, final Throwable exception, final String format, final Object... args) {
		logger.log(level, String.format(format, args), exception);
	}

	private void log(final Level level, final String format, final Object... data) {
		logger.log(level, String.format(format, data));
	}

	public void severe(final String format, final Object... args) {
		log(Level.ERROR, format, args);
	}

	public void warning(final String format, final Object... args) {
		log(Level.WARN, format, args);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("logger", logger).toString();
	}
}
