package pw.stamina.munition.event.core.selection;

import java.util.regex.Pattern;

/**
 * @author Mark Johnson
 */
public class RegexSelector<K extends CharSequence> extends AbstractBiSelector<K, Pattern> {

    public RegexSelector(final Pattern pattern) {
        super(pattern);
    }

    public RegexSelector(final String key, final int flags) {
        this(Pattern.compile(key, flags));
    }

    public RegexSelector(final String key) {
        this(key, 0);
    }

    @Override
    public boolean canSelect(final K chars) {
        return key.matcher(chars).matches();
    }
}
