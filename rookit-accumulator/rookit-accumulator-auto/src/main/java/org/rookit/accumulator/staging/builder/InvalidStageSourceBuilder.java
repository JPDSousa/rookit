
package org.rookit.accumulator.staging.builder;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

class InvalidStageSourceBuilder implements StageSourceBuilder {

    private final String typeName;
    private final Messager messager;

    InvalidStageSourceBuilder(final TypeElement elementType, final Messager messager) {
        super();
        this.typeName = elementType.getSimpleName().toString();
        this.messager = messager;
    }

    public String getElementName() {
        return this.typeName;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void writeTo(TypeElement element, final Filer filer) throws IOException {
        this.messager.printMessage(Diagnostic.Kind.ERROR, getElementName()
                + " cannot be written, as it is not a valid stageable release");
    }

}
