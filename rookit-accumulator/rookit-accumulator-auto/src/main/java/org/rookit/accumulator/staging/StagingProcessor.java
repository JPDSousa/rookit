
package org.rookit.accumulator.staging;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import one.util.streamex.StreamEx;

import org.rookit.accumulator.staging.builder.StageSourceBuilder;
import org.rookit.accumulator.staging.builder.StageSourceBuilderFactory;

@SuppressWarnings("javadoc")
@AutoService(Processor.class)
public class StagingProcessor extends AbstractProcessor {

    private static final SourceVersion SUPPORTED_VERSION = SourceVersion.latestSupported();
    private static final Set<String> SUPPORTED_ANNOTATIONS = ImmutableSet.of(StagingAccumulator.class.getName());

    private StageSourceBuilderFactory stageSourceBuiderFactory;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SUPPORTED_VERSION;
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.stageSourceBuiderFactory = new StageSourceBuilderFactory(this.processingEnv);
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        StreamEx.of(roundEnv.getElementsAnnotatedWith(StagingAccumulator.class))
                .filter(this::isClass)
                .select(TypeElement.class)
                .distinct()
                .map(this::createStageClass)
                .filter(StageSourceBuilder::isValid)
                .forEach(this::write);
        return false;
    }

    private StageSourceBuilder createStageClass(final TypeElement typeToBeStaged) {
        return this.stageSourceBuiderFactory.create(typeToBeStaged);
    }

    private void error(final String errorMessage, final Throwable throwable) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, errorMessage
                + throwable.getMessage()
                + throwable.getStackTrace());
    }

    private boolean isClass(final Element element) {
        final boolean isClass = element.getKind() == ElementKind.CLASS;
        if (!isClass) {
            final String message = String.format("%s is meant to be used only in classes, but was tagged in %s",
                    StagingAccumulator.class, element);
            warn(message);
        }
        return isClass;
    }

    private void warn(final String warnMessage) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, warnMessage);
    }

    private void write(final StageSourceBuilder sourceBuilder) {
        try {
            sourceBuilder.writeTo(, this.processingEnv.getFiler());
        } catch (final IOException e) {
            error("Cannot write to file", e);
        }
    }

}
