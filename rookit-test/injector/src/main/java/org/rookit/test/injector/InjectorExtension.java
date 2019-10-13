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
package org.rookit.test.injector;

import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

@SuppressWarnings("PublicConstructor") // JUnit requirement
public final class InjectorExtension implements ParameterResolver, BeforeEachCallback {

    private Injector injector;

    @Override
    public void beforeEach(final ExtensionContext context) {
        this.injector = Guice.createInjector(retrieveAnnotationFromTestClasses(context));

        context.getTestInstance()
                .ifPresent(this.injector::injectMembers);
    }

    private Collection<Module> retrieveAnnotationFromTestClasses(final ExtensionContext context) {
        return context.getTestClass()
                .map(testClass -> findAnnotationRecursively(testClass, RegisterModule.class))
                .orElse(StreamEx.empty())
                .append(StreamEx.of(findAnnotation(context.getElement(), RegisterModule.class)))
                .map(RegisterModule::value)
                .flatMap(Arrays::stream)
                .map(this::initModule)
                .toImmutableSet();
    }

    private <A extends Annotation> StreamEx<A> findAnnotationRecursively(final Class<?> clazz,
                                                                         final Class<A> annotationClass) {
        final StreamEx<A> superClassAnnotations = StreamEx.of(clazz.getSuperclass())
                .nonNull()
                .flatMap(superClass -> findAnnotationRecursively(superClass, annotationClass));

        return StreamEx.of(clazz.getInterfaces())
                .flatMap(interfaceClass -> findAnnotationRecursively(interfaceClass, annotationClass))
                .append(superClassAnnotations)
                .append(StreamEx.of(findAnnotation(clazz, annotationClass)));
    }

    private Module initModule(final Class<? extends Module> module) {
        try {
            return (Module) module.getMethod("getModule").invoke(null);
        } catch (final IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.isAnnotated(Inject.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException {
        //noinspection unchecked
        final Class<Object> type = (Class<Object>) parameterContext.getParameter().getType();
        final Key<Object> key = findBindingAnnotation(parameterContext.getParameter())
                .map(annotation -> Key.get(type, annotation))
                .orElse(Key.get(type));

        return this.injector.getInstance(key);
    }

    private Optional<Annotation> findBindingAnnotation(final AnnotatedElement parameter) {
        return Arrays.stream(parameter.getAnnotations())
                .filter(this::isBindingAnnotation)
                .findFirst();
    }

    private boolean isBindingAnnotation(final Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getAnnotations())
                .anyMatch(BindingAnnotation.class::isInstance);
    }

    @Override
    public String toString() {
        return "InjectorExtension{" +
                "injector=" + this.injector +
                "}";
    }
}
