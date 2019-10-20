/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.auto.javax.runtime.mirror.executable;

import io.reactivex.Completable;
import org.rookit.utils.graph.DependencyWrapper;
import org.rookit.utils.graph.MultiDependencyWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import java.lang.annotation.Annotation;
import java.util.List;

final class ExecutableTypeImpl implements RuntimeExecutableType {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecutableTypeImpl.class);

    private final AnnotatedConstruct annotatedConstruct;
    private final MultiDependencyWrapper<TypeVariable> typeVariables;
    private final DependencyWrapper<TypeMirror> returnType;
    private final MultiDependencyWrapper<TypeMirror> parameterTypes;
    private final DependencyWrapper<TypeMirror> receiverType;
    private final MultiDependencyWrapper<TypeMirror> thrownTypes;
    private final NoType noType;


    ExecutableTypeImpl(
            final AnnotatedConstruct annotatedConstruct,
            final MultiDependencyWrapper<TypeVariable> typeVariables,
            final DependencyWrapper<TypeMirror> returnType,
            final MultiDependencyWrapper<TypeMirror> parameterTypes,
            final DependencyWrapper<TypeMirror> receiverType,
            final MultiDependencyWrapper<TypeMirror> thrownTypes,
            final NoType noType) {
        this.annotatedConstruct = annotatedConstruct;
        this.typeVariables = typeVariables;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.receiverType = receiverType;
        this.thrownTypes = thrownTypes;
        this.noType = noType;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable typeVariables(final List<? extends TypeVariable> typeVariables) {
        logger.trace("Delegating to type variables dependency");
        return this.typeVariables.set(typeVariables);
    }

    @Override
    public Completable returnType(final TypeMirror returnType) {
        logger.trace("Delegating tot return type dependency");
        return this.returnType.set(returnType);
    }

    @Override
    public Completable parameterTypes(final List<? extends TypeMirror> parameterTypes) {
        logger.trace("Delegating to parameter types dependency");
        return this.parameterTypes.set(parameterTypes);
    }

    @Override
    public Completable receiverType(final TypeMirror receiverType) {
        logger.trace("Delegating to receiver type dependency");
        return this.receiverType.set(receiverType);
    }

    @Override
    public Completable thrownTypes(final List<? extends TypeMirror> thrownTypes) {
        logger.trace("Delegating to thrown types dependency");
        return this.thrownTypes.set(thrownTypes);
    }

    @Override
    public List<? extends TypeVariable> typeVariables() {
        logger.trace("Delegating to type variables dependency");
        return this.typeVariables.get();
    }

    @Override
    public TypeMirror returnType() {
        logger.trace("Delegating to return type dependency");
        return this.returnType.get().orElse(this.noType);
    }

    @Override
    public List<? extends TypeMirror> parameterTypes() {
        logger.trace("Delegating to parameter types dependency");
        return this.parameterTypes.get();
    }

    @Override
    public TypeMirror receiverType() {
        logger.trace("Delegating to receiver type dependency");
        return this.receiverType.get()
                .orElse(this.noType);
    }

    @Override
    public List<? extends TypeMirror> thrownTypes() {
        logger.trace("Delegating to thrown types dependency");
        return this.thrownTypes.get();
    }

    @Override
    public String toString() {
        return "ExecutableTypeImpl{" +
                "annotatedConstruct=" + this.annotatedConstruct +
                ", typeVariables=" + this.typeVariables +
                ", returnType=" + this.returnType +
                ", parameterTypes=" + this.parameterTypes +
                ", receiverType=" + this.receiverType +
                ", thrownTypes=" + this.thrownTypes +
                ", noType=" + this.noType +
                "}";
    }

}
