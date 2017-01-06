package pw.stamina.munition.feature.plugin.loading.configuration.parsing;

import pw.stamina.munition.core.versioning.Version;
import pw.stamina.munition.core.versioning.VersionTag;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Mark Johnson
 */
public final class SemanticVersionParser {

    private final int major;

    private final int minor;

    private final int patch;

    private final String[] preRelease;

    private final String[] buildMeta;

    private final int[] vParts;

    private final ArrayList<String> preParts;

    private final ArrayList<String> metaParts;

    private final char[] input;

    private int errPos;

    public static Version parseVersion(final String version) throws ParseException {
        final SemanticVersionParser parser = new SemanticVersionParser(version);
        return new Version(parser.major, parser.minor, parser.patch,
                (parser.preRelease.length > 0 ? Collections.singleton(VersionTag.SNAPSHOT) : Collections.emptySet()),
                Arrays.asList(parser.buildMeta));
    }

    private SemanticVersionParser(final String version) throws ParseException {
        vParts = new int[3];
        preParts = new ArrayList<String>(5);
        metaParts = new ArrayList<String>(5);
        input = version.toCharArray();
        if (!stateMajor()) { // Start recursive descend
            throw new ParseException(version, errPos);
        }
        major = vParts[0];
        minor = vParts[1];
        patch = vParts[2];
        preRelease = preParts.toArray(new String[preParts.size()]);
        buildMeta = metaParts.toArray(new String[metaParts.size()]);
    }

    private boolean stateMajor() {
        int pos = 0;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == 0) { // Empty String -> Error
            return false;
        }

        vParts[0] = Integer.parseInt(new String(input, 0, pos), 10);

        return input[pos] == '.' && stateMinor(pos + 1);

    }

    private boolean stateMinor(final int index) {
        int pos = index;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++;// match [0..9]+
        }
        if (pos == index) { // Empty String -> Error
            errPos = index;
            return false;
        }
        vParts[1] = Integer.parseInt(new String(input, index, pos - index), 10);

        if (input[pos] == '.') {
            return statePatch(pos + 1);
        }

        errPos = pos;
        return false;
    }

    private boolean statePatch(final int index) {
        int pos = index;
        while (pos < input.length && input[pos] >= '0' && input[pos] <= '9') {
            pos++; // match [0..9]+
        }
        if (pos == index) { // Empty String -> Error
            errPos = index;
            return false;
        }

        vParts[2] = Integer.parseInt(new String(input, index, pos - index), 10);

        if (pos == input.length) { // We have a clean version string
            return true;
        }

        if (input[pos] == '+') { // We have build meta tags -> descend
            return stateMeta(pos + 1);
        }

        if (input[pos] == '-') { // We have pre release tags -> descend
            return stateRelease(pos + 1);
        }

        errPos = pos; // We have junk
        return false;
    }

    private boolean stateRelease(final int index) {
        int pos = index;
        while ((pos < input.length)
                && ((input[pos] >= '0' && input[pos] <= '9')
                || (input[pos] >= 'a' && input[pos] <= 'z')
                || (input[pos] >= 'A' && input[pos] <= 'Z') || input[pos] == '-')) {
            pos++; // match [0..9a-zA-Z-]+
        }
        if (pos == index) { // Empty String -> Error
            errPos = index;
            return false;
        }

        preParts.add(new String(input, index, pos - index));
        if (pos == input.length) { // End of input
            return true;
        }
        if (input[pos] == '.') { // More parts -> descend
            return stateRelease(pos + 1);
        }
        if (input[pos] == '+') { // Build meta -> descend
            return stateMeta(pos + 1);
        }

        errPos = pos;
        return false;
    }

    private boolean stateMeta(final int index) {
        int pos = index;
        while ((pos < input.length)
                && ((input[pos] >= '0' && input[pos] <= '9')
                || (input[pos] >= 'a' && input[pos] <= 'z')
                || (input[pos] >= 'A' && input[pos] <= 'Z') || input[pos] == '-')) {
            pos++; // match [0..9a-zA-Z-]+
        }
        if (pos == index) { // Empty String -> Error
            errPos = index;
            return false;
        }

        metaParts.add(new String(input, index, pos - index));
        if (pos == input.length) { // End of input
            return true;
        }
        if (input[pos] == '.') { // More parts -> descend
            return stateMeta(pos + 1);
        }
        errPos = pos;
        return false;
    }
}