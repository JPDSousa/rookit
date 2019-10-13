package org.rookit.parser.format.field;

import it.unimi.dsi.fastutil.objects.Object2ShortArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.string.StringUtils;

abstract class AbstractField implements Field {

    private static final Object2ShortMap<String> BASE_TOKENS = new Object2ShortArrayMap<>();
    static final short SEVERE = -20;
    static final short LOW = -10;

    static {
        BASE_TOKENS.put("[", SEVERE);
        BASE_TOKENS.put("]", SEVERE);
        BASE_TOKENS.put(" - ", SEVERE);
        BASE_TOKENS.put("_", SEVERE);
        BASE_TOKENS.put("featuring", LOW);
        BASE_TOKENS.put("feat.", LOW);
        BASE_TOKENS.put("feat ", LOW);
        BASE_TOKENS.put("ft.", LOW);
        BASE_TOKENS.put("ft ", LOW);
    }

    private final StringUtils stringUtils;
    private final int score;

    AbstractField(final StringUtils stringUtils, final InitialScore score) {
        this.stringUtils = stringUtils;
        this.score = score.getScore();
    }

    @Override
    public int getInitialScore() {
        return this.score;
    }

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        if(value.isEmpty()) {
            return SEVERE;
        }
        return this.score + BASE_TOKENS.keySet().stream()
                .mapToInt(token -> BASE_TOKENS.getShort(token) * this.stringUtils.countMatchesIgnoreCase(value, token))
                .sum();
    }

    @Override
    public boolean isValidValue(final String value) {
        return !value.isEmpty();
    }

    @Override
    public String toString() {
        return "AbstractField{" +
                "stringUtils=" + this.stringUtils +
                ", score=" + this.score +
                "}";
    }
}
