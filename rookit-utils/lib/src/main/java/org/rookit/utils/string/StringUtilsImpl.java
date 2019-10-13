package org.rookit.utils.string;

import com.google.inject.Inject;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import java.util.Locale;

public final class StringUtilsImpl implements StringUtils {

    private final OptionalFactory optionalFactory;
    private final Locale locale;

    @Inject
    private StringUtilsImpl(final OptionalFactory optionalFactory, final Locale locale) {
        this.optionalFactory = optionalFactory;
        this.locale = locale;
    }

    @Override
    public String capitalizeFirstChar(final String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Cannot apply capitalization of the first char in an empty string.");
        }
        if (str.length() == 1) {
            return str.toUpperCase(this.locale);
        }

        return str.substring(0, 1).toUpperCase(this.locale) + str.substring(1);
    }

    @Override
    public int countMatchesIgnoreCase(final String str, final String sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(
                str.toLowerCase(this.locale),
                sub.toLowerCase(this.locale)
        );
    }

    @Override
    public Optional<String> getWithin(final String str, final String init, final String end){
        final int initIndex = str.indexOf(init);
        if(initIndex >= 0){
            return str.contains(end)
                    ? this.optionalFactory.of(str.substring(initIndex + init.length(), str.indexOf(end, initIndex)))
                    : this.optionalFactory.of(str.substring(initIndex));
        }
        return this.optionalFactory.empty();
    }

    @Override
    public String preview(final String content, final int characters) {
        return content.substring(0, characters) + "...";
    }

    @Override
    public String toString() {
        return "StringUtilsImpl{" +
                "optionalFactory=" + this.optionalFactory +
                ", locale=" + this.locale +
                "}";
    }
}
