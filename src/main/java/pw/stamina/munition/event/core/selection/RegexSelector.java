package pw.stamina.munition.event.core.selection;

import java.util.regex.Pattern;

/**
 * @author Mark Johnson
 */
public class RegexSelector<K extends CharSequence> extends AbstractBiSelector<K, Pattern> {

    public RegexSelector(final Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean canSelect(final K chars) {
        return key.matcher(chars).matches();
    }
}
