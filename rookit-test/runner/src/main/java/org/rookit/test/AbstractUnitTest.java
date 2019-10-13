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

package org.rookit.test;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rookit.failsafe.FailsafeModule;
import org.rookit.test.assertj.Conditions;
import org.rookit.test.exception.ResourceCreationException;
import org.rookit.test.guice.GuiceTests;
import org.rookit.test.guice.TestModule;
import org.rookit.test.injector.InjectorExtension;
import org.rookit.test.injector.RegisterModule;
import org.rookit.utils.guice.UtilsModule;
import org.rookit.utils.reflect.ClassVisitor;
import org.rookit.utils.reflect.ExtendedClass;
import org.rookit.utils.reflect.ExtendedClassFactory;
import org.rookit.utils.reflect.ExtendedMethod;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SuppressWarnings("javadoc")
@ExtendWith(MockitoExtension.class)
@ExtendWith(InjectorExtension.class)
@RegisterModule({FailsafeModule.class, UtilsModule.class, TestModule.class})
public abstract class AbstractUnitTest<T> implements ObjectTest<T>, ArgumentsTest<T>, ArchTest {

    @Inject
    @GuiceTests
    private ClassVisitor<Object> mockVisitor;

    @Inject
    private ExtendedClassFactory classFactory;

    @Inject
    @GuiceTests
    private Conditions conditions;

    private T testResource;
    private JavaClasses javaClass;

    @Override
    public JavaClasses getJavaClasses() {
        return this.javaClass;
    }

    @Override
    public T getTestResource() {
        return this.testResource;
    }

    @Override
    public final T createTestResource() throws ResourceCreationException {
        return doCreateTestResource();
    }

    protected abstract T doCreateTestResource() throws ResourceCreationException;

    @BeforeEach
    public final void setupGuineaPig() throws ResourceCreationException {
        this.testResource = createTestResource();
        this.javaClass = new ClassFileImporter().importClasses(this.testResource.getClass());
    }

    @Test
    public final void testCreatorDoesNotReturnNull() throws ResourceCreationException {
        assertThat(createTestResource())
                .as("Creating a non null test object")
                .isNotNull();
    }

    @TestFactory
    public final Collection<DynamicTest> testMethodReturnValue() {
        final ExtendedClass<T> clazz = extendedClassFactory().create(this.testResource);

        return clazz.methods().stream()
                .filter(ExtendedMethod::isCallable)
                .flatMap(this::createMethodTest)
                .collect(Collectors.toSet());
    }

    private Stream<DynamicTest> createMethodTest(final ExtendedMethod<T, ?> method) {
        final ImmutableList.Builder<DynamicTest> testBuilder = ImmutableList.builderWithExpectedSize(2);
        final DynamicTest returnValueTest = dynamicTest(String.format("Return value of %s cannot be null",
                method.methodName()),
                () -> assertThat(method.invocation().invoke(createTestResource()))
                        .as("Invoking field %s of class %s does not return null",
                                method.methodName(), method.className())
                        .isNotNull());
        testBuilder.add(returnValueTest);

        if (Collection.class.isAssignableFrom(method.returnType().original())) {
            final DynamicTest collectionTest = dynamicTest(String.format("Collection from %s is immutable",
                    method.methodName()),
                    () -> assertThat((Iterable<?>) method.invocation().invoke(createTestResource()))
                            .as("The collection returned by %s::%s", method.className(), method.methodName())
                            .is(this.conditions.isImmutableCollection()));

            testBuilder.add(collectionTest);
        }

        return testBuilder.build().stream();
    }

    @Override
    public ClassVisitor<Object> mockVisitor() {
        return this.mockVisitor;
    }

    @Override
    public ExtendedClassFactory extendedClassFactory() {
        return this.classFactory;
    }
}
