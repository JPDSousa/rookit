package org.rookit.utils.stage;

@SuppressWarnings("javadoc")
public interface NonPrimaryStage<S extends Stageable<S, F>, F> extends Stageable<S, F> {
    
    S getPreviousStage();

}
