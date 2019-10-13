package org.rookit.failsafe;

import org.slf4j.Logger;

public interface Failsafe {

    AccessorObjectFailsafe checkArgument();

    AccessorObjectFailsafe checkState();

    Logger getLogger(Class<?> clazz);

    ExceptionHandler handleException();
}
