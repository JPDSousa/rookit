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

import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.rookit.utils.reflect.ClassVisitor;
import org.rookit.utils.reflect.ExtendedClass;
import org.rookit.utils.reflect.ExtendedClassFactory;
import org.rookit.utils.reflect.ExtendedMethod;
import org.rookit.utils.reflect.MethodInvocation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public interface ArgumentsTest<T> extends RookitTest<T> {

    ExtendedClassFactory extendedClassFactory();

    @TestFactory
    default Stream<DynamicTest> testArgumentsNotNull() {
        final ExtendedClass<T> clazz = extendedClassFactory().create(getTestResource());

        return clazz.methods().stream()
                .filter(method -> !method.parameters().isEmpty())
                .filter(method -> !"equals".equals(method.methodName()))
                .flatMap(this::cylcleNull)
                .map(this::createArgumentTest);
    }

    default DynamicTest createArgumentTest(final MethodInvocation<T, ?> invocation) {
        final ExtendedMethod<?, ?> method = invocation.method();
        final String display = String.format("Method %s::%s cannot accept null values in the form of: '%s'",
                method.className(), method.methodName(), Arrays.toString(invocation.arguments()));

        return dynamicTest(display, () -> assertThatThrownBy(() -> invocation.invoke(createTestResource()))
                .as("Method %s::%s cannot accept null values in the form of: '%s'",
                        method.className(), method.methodName(), Arrays.toString(invocation.arguments()))
                .isInstanceOf(IllegalArgumentException.class));
    }

    @SuppressWarnings("AssignmentToNull")
    default <R> StreamEx<MethodInvocation<T, R>> cylcleNull(final ExtendedMethod<T, R> method) {
        final List<ExtendedClass<?>> parameters = method.parameters();
        final int length = parameters.size();
        final Object[] mocks = createMocks(parameters);

        return IntStreamEx.range(length)
                .mapToObj(index -> {
                    final Object[] arguments = ArrayUtils.clone(mocks);
                    arguments[index] = null;
                    return method.invocation(arguments);
                });
    }

    default Object[] createMocks(final List<ExtendedClass<?>> types) {
        final int parameterCount = types.size();
        final Object[] objects = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            objects[i] = types.get(i).accept(mockVisitor());
        }
        return objects;
    }

    ClassVisitor<Object> mockVisitor();

}
