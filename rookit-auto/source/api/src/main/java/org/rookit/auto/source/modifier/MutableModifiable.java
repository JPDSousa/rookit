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
package org.rookit.auto.source.modifier;

import com.google.common.collect.ImmutableList;

import javax.lang.model.element.Modifier;

import java.util.Collection;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.DEFAULT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public interface MutableModifiable<S extends MutableModifiable<S>> extends Modifiable {

    S removeModifiers(Collection<Modifier> modifiers);

    S resetVisibility();

    S addModifiers(Collection<Modifier> modifiers);

    default S addModifier(final Modifier modifiers) {
        return addModifiers(ImmutableList.of(modifiers));
    }

    default S makePrivate() {
        return resetVisibility()
                .addModifier(PRIVATE);
    }

    default S makePackagePrivate() {
        return resetVisibility();
    }

    default S makeProtected() {
        return resetVisibility()
                .addModifier(PROTECTED);
    }

    default S makePublic() {
        return resetVisibility()
                .addModifier(PUBLIC);
    }

    default S makeFinal() {
        return removeModifiers(ImmutableList.of(ABSTRACT))
                .addModifier(FINAL);
    }

    default S makeAbstract() {
        return removeModifiers(ImmutableList.of(FINAL))
                .addModifier(ABSTRACT);
    }

    default S makeDefault() {
        return addModifier(DEFAULT);
    }

    default S makeStatic() {
        return addModifier(STATIC);
    }

}
