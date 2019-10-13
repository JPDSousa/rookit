package org.rookit.utils.stage;

@FunctionalInterface
@SuppressWarnings("javadoc")
public interface Stageable<S extends Stageable<S, F>, F> {
    
    S createStage(F factory);

}
