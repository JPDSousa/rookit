
package org.rookit.accumulator.staging.builder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import one.util.streamex.StreamEx;

import org.apache.commons.lang3.RandomUtils;
import org.rookit.utils.stage.NonPrimaryStage;

class JavaPoetStageSourceBuilder implements StageSourceBuilder {

    private static final String PREVIOUS_STAGE_FIELD_NAME = "previousStage";
    private static final String ACCUMULATOR_LOCAL_NAME = "accumulator";
    
    private final String typeName;
    private final JavaFile stageJavaFile;
    private final TypeElement superType;
    private final Types typeUtils;

    JavaPoetStageSourceBuilder(final Types typeUtils, final TypeElement superType) {
        this.typeName = "Staged" + superType.getSimpleName();
        this.superType = superType;
        this.typeUtils = typeUtils;
        
        final TypeName typeName = TypeName.get(superType.asType());
        
        final Collection<MethodSpec> methods = streamMethods()
                .filter(method -> method.getModifiers().contains(Modifier.PUBLIC))
                .filter(method -> !method.getModifiers().contains(Modifier.STATIC))
                .filter(method -> method.getSimpleName().toString().endsWith("Accumulator"))
                .map(this::buildMethod)
                .collect(Collectors.toSet());
        
        final TypeSpec stageType = TypeSpec.classBuilder(this.typeName)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(superType.asType()))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(NonPrimaryStage.class), typeName))
                .addField(generateSerialVersionUID())
                .addField(createPreviousStage(typeName))
                .addMethod(createConstructor(typeName))
                .addMethods(methods)
                .build();
        
        this.stageJavaFile = JavaFile.builder(superType.getEnclosingElement().getSimpleName().toString(), stageType)
                .build();
    }
    
    private StreamEx<ExecutableElement> streamMethods() {
        return StreamEx.of(this.superType.getEnclosedElements().stream())
                .select(ExecutableElement.class);
    }
    
    private static MethodSpec createConstructor(final TypeName superClass) {
        return MethodSpec.constructorBuilder()
                .addParameter(superClass, PREVIOUS_STAGE_FIELD_NAME, Modifier.FINAL)
                .addStatement("super($T)", PREVIOUS_STAGE_FIELD_NAME)
                .addStatement("this.$T = $T", PREVIOUS_STAGE_FIELD_NAME, PREVIOUS_STAGE_FIELD_NAME)
                .build();
    }
    
    private static FieldSpec createPreviousStage(final TypeName previousStageType) {
        return FieldSpec.builder(previousStageType, PREVIOUS_STAGE_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();
    }
    
    
    private MethodSpec buildMethod(final ExecutableElement method) {
        return MethodSpec.methodBuilder(method.getSimpleName().toString())
                .addAnnotation(Override.class)
                .returns(TypeName.get(method.getReturnType()))
                .addStatement(createAccumulator(method.getReturnType()))
                
                .build();
    }
    
    private CodeBlock createAccumulator(final TypeMirror accumulatorType) {
        final TypeElement fullType = (TypeElement) this.typeUtils.asElement(accumulatorType);
        return CodeBlock.of("final $T $T = getAccumulatorFactory().create($T.class)", 
                accumulatorType,
                ACCUMULATOR_LOCAL_NAME,
                fullType.getSimpleName());
    }
    
    @SuppressWarnings("boxing")
    private static FieldSpec generateSerialVersionUID() {
        return FieldSpec.builder(long.class, "serialVersionUID")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L", RandomUtils.nextLong())
                .build();
    }

    @Override
    public void writeTo(TypeElement element, final Filer filer) throws IOException {
        this.stageJavaFile.writeTo(filer);
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
