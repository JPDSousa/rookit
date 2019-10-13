package org.rookit.utils.optional;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@SuppressWarnings("BooleanParameter")
public interface OptionalBoolean {

    boolean isPresent();

    boolean getAsBoolean();

    default boolean orElseFalse() {
        return orElse(false);
    }

    default boolean orElseTrue() {
        return orElse(true);
    }

    default boolean orElse(final boolean other) {
        return isPresent() ? getAsBoolean() : other;
    }

    default boolean orElse(final BooleanSupplier supplier) {
        return isPresent() ? getAsBoolean() : supplier.getAsBoolean();
    }

    default <X extends Throwable> boolean orElseThrow(final Supplier<X> exceptionSupplier) throws X {
        if (isPresent()) {
            return getAsBoolean();
        }
        throw exceptionSupplier.get();
    }

    OptionalBoolean ifPresent(BooleanConsumer consumer);
}
