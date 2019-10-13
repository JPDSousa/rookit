package org.rookit.utils.optional;

import it.unimi.dsi.fastutil.shorts.ShortConsumer;

import javax.annotation.concurrent.Immutable;

@Immutable
class EmptyShort implements OptionalShort {

    @Override
    public boolean isPresent() {
        return false;
    }

    @SuppressWarnings("ProhibitedExceptionThrown")
    @Override
    public short getAsShort() {
        throw new NullPointerException("This optional is empty.");
    }

    @Override
    public OptionalShort ifPresent(final ShortConsumer consumer) {
        return this;
    }

    @Override
    public String toString() {
        return "EmptyShort{}";
    }
}
