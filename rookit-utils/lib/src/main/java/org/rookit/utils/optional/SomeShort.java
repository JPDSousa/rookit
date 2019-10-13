package org.rookit.utils.optional;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.shorts.ShortConsumer;

final class SomeShort implements OptionalShort {

    private final short value;

    SomeShort(final short value) {
        this.value = value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public short getAsShort() {
        return this.value;
    }

    @Override
    public OptionalShort ifPresent(final ShortConsumer consumer) {
        consumer.accept(this.value);
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", this.value)
                .toString();
    }
}
