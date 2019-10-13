package accumulator.dm;

import one.util.streamex.StreamEx;
import org.apache.commons.beanutils.BeanUtils;
import accumulator.AbstractSingleAccumulator;
import accumulator.Accumulator;
import accumulator.SingleAccumulator;
import org.rookit.utils.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public abstract class AbstractDataModelAccumulator<A extends SingleAccumulator<A, T, DataModelAccumulatorFactory>, T>
        extends AbstractSingleAccumulator<A, T, DataModelAccumulatorFactory> {

    private static final long serialVersionUID = -1103123416883451357L;

    protected abstract Optional<T> constructResult();

    @Override
    public Optional<T> get() {
        final Optional<T> resultOrNone = constructResult();
        resultOrNone.ifPresent(result -> copyProperties(result, this));

        return resultOrNone;
    }

    private static void copyProperties(final Object destination, final Object source) {
        try {
            BeanUtils.copyProperties(destination, source);
        } catch (final IllegalAccessException e) {
            VALIDATOR.handleException().runtimeException(e);
        } catch (final InvocationTargetException e) {
            VALIDATOR.handleException().runtimeException(e.getCause());
        }
    }

    @Override
    public void accumulate(final T source) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(source, "source");

        copyProperties(this, source);
    }

    @Override
    public void accumulate(final A accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        copyProperties(this, accumulator);

    }

    @Override
    public boolean isEmpty() {
        return StreamEx.of(getClass().getMethods())
                .filter(method -> MethodUtils.hasReturnTypeOf(method, Accumulator.class))
                .map(this::invokeAccumulatorGetter)
                .allMatch(Accumulator::isEmpty);
    }

    private Accumulator<?, ?, ?> invokeAccumulatorGetter(final Method method) {
        try {
            return (Accumulator<?, ?, ?>) method.invoke(this);
        } catch (final IllegalAccessException e) {
            return VALIDATOR.handleException().runtimeException(e);
        } catch (final InvocationTargetException e) {
            return VALIDATOR.handleException().runtimeException(e.getCause());
        }
    }
}
