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
package org.rookit.auto.javapoet.type.reference;

import com.squareup.javapoet.TypeName;
import org.rookit.auto.source.CodeSourceVisitor;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

final class BaseJavaPoetReference implements JavaPoetReference {

    private final String packageName;
    private final TypeName javaPoet;
    private final OptionalFactory optionalFactory;

    BaseJavaPoetReference(final String packageName, final TypeName javaPoet, final OptionalFactory optionalFactory) {
        this.packageName = packageName;
        this.javaPoet = javaPoet;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public TypeName buildTypeName() {
        return this.javaPoet;
    }

    @Override
    public <R, P> R accept(final CodeSourceVisitor<R, P> visitor, final P parameter) {

        return visitor.visitReference(this, parameter);
    }

    @Override
    public Optional<String> packageName() {

        return this.packageName.isEmpty()
                ? this.optionalFactory.empty()
                : this.optionalFactory.of(this.packageName);
    }

}
