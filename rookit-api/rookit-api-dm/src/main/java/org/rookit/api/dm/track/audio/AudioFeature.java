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
package org.rookit.api.dm.track.audio;

import com.google.common.collect.Range;
import com.kekstudio.musictheory.Key;
import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalShort;

import java.util.Optional;
import java.util.OptionalDouble;

@SuppressWarnings({"javadoc", "ConstantDeclaredInInterface"})
@PropertyContainer
public interface AudioFeature extends AudioFeatureSetter {

    Range<Short> RANGE_BPM = Range.openClosed((short) 0, (short) 400);
    Range<Double> RANGE_DANCEABILITY = Range.open(0.0, 1.0);
    Range<Double> RANGE_ENERGY = Range.open(0.0, 1.0);
    Range<Double> RANGE_VALENCE = Range.open(0.0, 1.0);

    @Property
    OptionalShort bpm();

    @Property
    OptionalDouble danceability();

    @Property
    OptionalDouble energy();

    @Property
    Optional<Key> trackKey();

    @Property
    OptionalDouble valence();

    @Property
    OptionalBoolean acoustic();

    @Property
    OptionalBoolean instrumental();

    @Property
    OptionalBoolean live();
}
