
package org.rookit.accumulator.staging.builder;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import org.rookit.utils.stage.Stageable;

@SuppressWarnings("javadoc")
public class StageSourceBuilderFactory {

    private final Types typeUtils;
    private final Collection<TypeElement> requiredInterfaces;
    private final Messager messager;

    public StageSourceBuilderFactory(final ProcessingEnvironment processingEnvironment) {
        super();
        this.messager = processingEnvironment.getMessager();
        this.typeUtils = processingEnvironment.getTypeUtils();
        
        final Elements elementUtils = processingEnvironment.getElementUtils();
        final TypeElement stageable = elementUtils.getTypeElement(Stageable.class.getCanonicalName());
        this.requiredInterfaces = ImmutableSet.of(stageable);
    }

    public StageSourceBuilder create(final TypeElement typeToBeStaged) {
        final Collection<? extends TypeMirror> interfaces = typeToBeStaged.getInterfaces();

        for (final TypeElement requiredInterface : this.requiredInterfaces) {
            if (!interfaces.contains(requiredInterface.asType())) {
                this.messager.printMessage(Diagnostic.Kind.WARNING,
                        String.format("%s does not implement %s, which is required by this processing stage.",
                                typeToBeStaged, requiredInterface));
                return new InvalidStageSourceBuilder(typeToBeStaged, this.messager);
            }
        }
        return new JavaPoetStageSourceBuilder(this.typeUtils, typeToBeStaged);
    }

}
