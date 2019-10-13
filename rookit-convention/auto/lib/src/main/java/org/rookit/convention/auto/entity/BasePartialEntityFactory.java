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
package org.rookit.convention.auto.entity;

import com.google.inject.Inject;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.CodeSource;
import org.rookit.auto.source.CodeSourceContainerFactory;
import org.rookit.auto.source.CodeSourceFactory;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.convention.auto.entity.parent.ParentExtractor;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.utils.optional.OptionalFactory;

import java.util.Collection;

public final class BasePartialEntityFactory implements CodeSourceFactory {

    public static CodeSourceFactory create(final IdentifierFactory identifierFactory,
                                           final SingleTypeSourceFactory typeSourceFactory,
                                           final OptionalFactory optionalFactory,
                                           final ParentExtractor parentExtractor,
                                           final CodeSourceContainerFactory containerFactory,
                                           final ConventionTypeElementFactory elementFactory) {
        return new BasePartialEntityFactory(identifierFactory, typeSourceFactory, optionalFactory,
                parentExtractor, containerFactory, elementFactory);
    }

    private final IdentifierFactory identifierFactory;
    private final SingleTypeSourceFactory typeSourceFactory;
    private final OptionalFactory optionalFactory;
    private final ParentExtractor parentExtractor;
    private final CodeSourceContainerFactory containerFactory;
    private final ConventionTypeElementFactory elementFactory;

    @Inject
    private BasePartialEntityFactory(final IdentifierFactory identifierFactory,
                                     final SingleTypeSourceFactory typeSourceFactory,
                                     final OptionalFactory optionalFactory,
                                     final ParentExtractor parentExtractor,
                                     final CodeSourceContainerFactory containerFactory,
                                     final ConventionTypeElementFactory elementFactory) {
        this.identifierFactory = identifierFactory;
        this.typeSourceFactory = typeSourceFactory;
        this.optionalFactory = optionalFactory;
        this.parentExtractor = parentExtractor;
        this.containerFactory = containerFactory;
        this.elementFactory = elementFactory;
    }

    @Override
    public CodeSource create(final ExtendedTypeElement element) {
        final Identifier identifier = this.identifierFactory.create(element);
        final ConventionTypeElement conventionElement = this.elementFactory.extendType(element);
        final Collection<CodeSource> parents = this.parentExtractor.extractAsIterable(conventionElement);
        final TypeSource source = this.typeSourceFactory.create(identifier, element);

        return new PartialEntityImpl(identifier, this.containerFactory.create(parents), source, this.optionalFactory);
    }

    @Override
    public String toString() {
        return "BasePartialEntityFactory{" +
                "identifierFactory=" + this.identifierFactory +
                ", typeSourceFactory=" + this.typeSourceFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", parentExtractor=" + this.parentExtractor +
                ", containerFactory=" + this.containerFactory +
                ", elementFactory=" + this.elementFactory +
                "}";
    }
}
