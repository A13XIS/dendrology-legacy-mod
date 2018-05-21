package inc.a13xis.legacy.koresample.common.util.lang;

import net.minecraft.util.text.translation.I18n;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Nullable
public class LangMap {
	//private static final Splitter SPLITTER = Splitter.on('=').limit(2);
	private static final Pattern PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	private Map<String, String> langmap = new HashMap<String, String>();

	public LangMap(InputStream inputStreamIn) throws IOException {
		inputStreamIn = net.minecraftforge.fml.common.FMLCommonHandler.instance().loadLanguage(langmap, inputStreamIn);
		if (inputStreamIn == null) return;
		for (String s : IOUtils.readLines(inputStreamIn, Charsets.UTF_8)) {
			if (!s.isEmpty() && s.charAt(0) != 35) {
				String[] astring = s.split("=", 2);

				if (astring != null && astring.length == 2) {
					String s1 = astring[0];
					String s2 = PATTERN.matcher(astring[1]).replaceAll("%$1s");
					this.langmap.put(s1, s2);
				}
			}
		}
	}

	public String formatAndSafeTranslate(LangMap alternative, String toTranslate, String... formatArgs) {
		if (toTranslate == null) throw new IllegalArgumentException("Translation Key must not be null");
		String s = net.minecraft.client.resources.I18n.format(toTranslate, formatArgs);
		if (s.contains(toTranslate)) {
			String temp = langmap.get(toTranslate);
			s = temp == null ? s : String.format(temp, formatArgs);
		}
		if (s.contains(toTranslate) && alternative != null) {
			s = alternative.formatAndSafeTranslate(alternative, toTranslate, formatArgs);
		}
		return s;
	}

	public String formatAndSafeServerTranslate(LangMap alternative, String toTranslate, String... formatArgs) {
		if (toTranslate == null) throw new IllegalArgumentException("Translation Key must not be null");
		String s = I18n.translateToLocalFormatted(toTranslate, formatArgs);
		if (s.contains(toTranslate)) {
			String temp = langmap.get(toTranslate);
			s = temp == null ? s : String.format(temp, formatArgs);
		}
		if (s.contains(toTranslate) && alternative != null) {
			s = alternative.formatAndSafeServerTranslate(alternative, toTranslate, formatArgs);
		}
		return s;
	}

}
