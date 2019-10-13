package accumulator.string;

import accumulator.AbstractSingleAccumulator;
import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;

class StringBufferAccumulatorImpl extends AbstractSingleAccumulator<StringBufferAccumulator, String, AccumulatorFactory>
        implements StringBufferAccumulator {

    private static final String LINE_SEPARATOR = String.format("%n");
    private static final long serialVersionUID = -8085113288095655651L;

    @SuppressWarnings("StringBufferField")
    private final StringBuffer values;

    StringBufferAccumulatorImpl() {
        this.values = new StringBuffer(32);
    }

    @Override
    public StringBuffer getValues() {
        return new StringBuffer(this.values);
    }

    @Override
    public void accumulate(final String source) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotEmpty(source, "source");
        this.values
                .append(source)
                .append(LINE_SEPARATOR)
                .append("---")
                .append(LINE_SEPARATOR);
    }

    @Override
    public void accumulate(final StringBufferAccumulator accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        accumulate(accumulator.getValues().toString());
    }

    @Override
    public boolean isEmpty() {
        return this.values.length() == 0;
    }

    @Override
    public StringBufferAccumulator createStage(final AccumulatorFactory factory) {
        return new StagedStringBufferAccumulatorImpl(this);
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("values", this.values)
                .toString();
    }
}
