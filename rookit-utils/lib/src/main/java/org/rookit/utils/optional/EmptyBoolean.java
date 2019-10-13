package org.rookit.utils.optional;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import javax.annotation.concurrent.Immutable;

@Immutable
class EmptyBoolean implements OptionalBoolean {

    @Override
    public boolean isPresent() {
        return false;
    }

    @SuppressWarnings("ProhibitedExceptionThrown")
    @Override
    public boolean getAsBoolean() {
        throw new NullPointerException("This optional is empty");
    }

    @Override
    public OptionalBoolean ifPresent(final BooleanConsumer consumer) {
        return this;
    }
}
