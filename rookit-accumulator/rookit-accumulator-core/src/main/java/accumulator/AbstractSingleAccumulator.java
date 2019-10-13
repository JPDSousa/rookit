
package accumulator;

import org.apache.logging.log4j.Logger;
import accumulator.validator.AccumulatorValidator;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public abstract class AbstractSingleAccumulator<A extends SingleAccumulator<A, T, F>, T, F extends AccumulatorFactory>
        implements SingleAccumulator<A, T, F> {

    protected static final Validator VALIDATOR = AccumulatorValidator.getInstance();
    
    /**
     * Logger for AbstractSingleAccumulator.
     */
    private static final Logger logger = VALIDATOR.getLogger(AbstractSingleAccumulator.class);
    private static final long serialVersionUID = 5256105437775445137L;

    @Override
    public T getOrFallback(final Supplier<T> fallback, final String fallbackMessage, final Object... messageArgs) {
        VALIDATOR.checkArgument().isNotNull(fallback, "fallback");
        VALIDATOR.checkArgument().isNotNull(fallbackMessage, "fallbackMessage");

        final Optional<T> resultOrNone = get();
        if (resultOrNone.isPresent()) {
            return resultOrNone.get();
        }
        logger.warn(fallbackMessage, messageArgs);
        return fallback.get();
    }

}
