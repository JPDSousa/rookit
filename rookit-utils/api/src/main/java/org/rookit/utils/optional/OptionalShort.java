package org.rookit.utils.optional;

import it.unimi.dsi.fastutil.shorts.ShortConsumer;
import org.rookit.utils.supplier.ShortSupplier;

import java.util.function.Supplier;

public interface OptionalShort {

    boolean isPresent();

    short getAsShort();

    default short orElse(final short other) {
        return isPresent() ? getAsShort() : other;
    }

    default short orElse(final ShortSupplier supplier) {
        return isPresent() ? getAsShort() : supplier.getAsShort();
    }

    default <X extends Throwable> short orElseThrow(final Supplier<X> exceptionSupplier) throws X {
        if (isPresent()) {
            return getAsShort();
        }
        throw exceptionSupplier.get();
    }

    OptionalShort ifPresent(ShortConsumer consumer);
}
