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
package org.rookit.auto.javapoet.type;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.rookit.auto.javax.naming.Identifier;

import javax.lang.model.element.Modifier;
import java.util.concurrent.Executor;

final class BaseTypeSource extends AbstractJavaPoetTypeSource {

    private final Identifier identifier;
    @SuppressWarnings("FieldNotUsedInToString")
    private final TypeSpec.Builder source;

    BaseTypeSource(final Identifier identifier,
                   final TypeSpec.Builder source,
                   final Executor executor) {
        super(executor);
        this.source = source;
        this.identifier = identifier;
    }

    @Override
    public Identifier identifier() {
        return this.identifier;
    }

    @Override
    public void addMethod(final MethodSpec method) {
        this.source.addMethod(method);
    }

    @Override
    public void addField(final FieldSpec field) {
        this.source.addField(field);
    }

    @Override
    protected TypeSpec typeSpec() {
        final TypeSpec spec = this.source.build();
        // TODO in the future please consider having different implementations for different kinds of specs.
        if ((spec.kind != TypeSpec.Kind.INTERFACE) && (spec.kind != TypeSpec.Kind.ANNOTATION)) {
            return createToString();
        }
        return spec;
    }

    private TypeSpec createToString() {
        final TypeSpec original = this.source.build();
        final CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("return \"$L{\" +\n", original.name);
        for (final FieldSpec field : original.fieldSpecs) {
            codeBlock.add("\"$L=\" + this.$L +\n", field.name, field.name);
        }
        codeBlock.add("\"} \" + super.toString();\n");

        final MethodSpec toString = MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(String.class)
                .addCode(codeBlock.build())
                .build();
        return original.toBuilder()
                .addMethod(toString)
                .build();
    }

    @Override
    public String toString() {
        return "BaseTypeSource{" +
                "identifier=" + this.identifier +
                "} " + super.toString();
    }
}
