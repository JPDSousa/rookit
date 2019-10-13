package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.rookit.parser.format.field.Field;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;
import org.rookit.parser.utils.ParserValidator;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

final class FieldParserFactoryImpl implements FieldParserFactory {

    private static final Validator VALIDATOR = ParserValidator.getDefault();

    private static final Logger logger = VALIDATOR.getLogger(FieldParserFactoryImpl.class);

    private static final char TAG_START = '<';
    private static final char TAG_END = '>';

    private final Predicate<String> validationRules;
    private final Map<String, Field> fields;
    private final TrackSlotParserResultFactory accumulatorFactory;

    @Inject
    private FieldParserFactoryImpl(final Map<String, Field> fields,
                                   final TrackSlotParserResultFactory accumulatorFactory) {
        this.accumulatorFactory = accumulatorFactory;
        this.validationRules = expression -> expression.indexOf("><") == 0;
        this.fields = fields;
    }

    @Override
    public FieldParser create(final String rawExpression) {
        VALIDATOR.checkArgument().isNotNull(rawExpression, "rawExpression");
        VALIDATOR.checkArgument().is(isValidExpression(rawExpression),
                "The expression '%s' is ambiguous",
                rawExpression);

        final FieldParser fieldParser = doCreate(rawExpression);
        return new EnhanceFieldParser(this.accumulatorFactory, fieldParser);
    }

    @Override
    public FieldParser create(final Collection<String> rawFormats) {
        final ImmutableSet.Builder<FieldParser> builder = ImmutableSet.builder();

        for (final String rawExpression : rawFormats) {
            builder.add(create(rawExpression));
        }

        return new MultiExpressionFieldParser(this.accumulatorFactory, builder.build());
    }

    private FieldParser doCreate(final String rawExpression) {
        if (rawExpression.isEmpty()) {
            return new EmptyFieldParser(this.accumulatorFactory, true);
        }

        final int indexOfTagStart = rawExpression.indexOf(TAG_START);
        if (indexOfTagStart >= 0) {
            return createDynamicField(rawExpression.substring(indexOfTagStart));
        }

        logger.warn("Expression '%s' contains no fields. A static dummy expression will be used");
        return new EmptyFieldParser(this.accumulatorFactory, true);
    }

    private FieldParser createDynamicField(final String rawExpression) {
        final int endTagCharIndex = rawExpression.indexOf(TAG_END);
        VALIDATOR.checkArgument().is(endTagCharIndex > 0,
                "Cannot find end tag char for expression '%s'", rawExpression);

        final String fieldName = rawExpression.substring(1, endTagCharIndex);
        final Field field = Optional.ofNullable(this.fields.get(fieldName))
                .orElseThrow(() -> VALIDATOR.handleException().runtimeException("No such field: " + fieldName));

        final int nextStartIndex = rawExpression.indexOf(TAG_START, endTagCharIndex);

        if (nextStartIndex >= 0) {
            final String delimiter = rawExpression.substring(endTagCharIndex + 1, nextStartIndex);
            final String nextExpression = rawExpression.substring(nextStartIndex);

            return new DynamicFieldParser(this.accumulatorFactory,
                    field,
                    delimiter,
                    doCreate(nextExpression));
        } else {
            return new TerminalDynamicFieldParser(this.accumulatorFactory, field);
        }

    }

    private boolean isValidExpression(final String expression) {
        return this.validationRules.test(expression);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("validationRules", this.validationRules)
                .add("fields", this.fields)
                .add("accumulatorFactory", this.accumulatorFactory)
                .toString();
    }
}
