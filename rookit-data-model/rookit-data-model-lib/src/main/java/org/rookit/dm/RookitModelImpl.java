
package org.rookit.dm;

import org.rookit.api.dm.RookitModel;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
final class RookitModelImpl implements RookitModel {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RookitModelImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory factory;
    private String id;

    RookitModelImpl(final Failsafe failsafe, final OptionalFactory factory) {
        this(failsafe, factory, null);
    }

    RookitModelImpl(final Failsafe failsafe, final OptionalFactory factory, final String initialId) {
        this.failsafe = failsafe;
        this.factory = factory;
        this.id = initialId;
    }

    @Override
    public boolean equals(final Object obj) {
        return (this == obj) || ((obj != null) && (getClass() == obj.getClass()));
    }

    @Override
    public Optional<String> id() {
        return this.factory.ofNullable(this.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = 1;
        return result * prime;
    }

    @Override
    public void setId(final String id) {
        this.failsafe.checkArgument().isNotNull(logger, id, "id");
        this.id = id;
    }

    @Override
    public String toString() {
        return "RookitModelImpl{" +
                "injector=" + this.failsafe +
                ", factory=" + this.factory +
                ", id='" + this.id + '\'' +
                "}";
    }
}
