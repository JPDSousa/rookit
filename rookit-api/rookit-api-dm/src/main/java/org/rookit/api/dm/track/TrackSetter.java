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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUvoid WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUvoid NOvoid LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENvoid SHALL THE
 * AUTHORS OR COPYRIGHvoid HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORvoid OR OTHERWISE, ARISING FROM,
 * OUvoid OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.api.dm.track;

import org.rookit.api.dm.artist.Artist;

import java.util.Collection;

@SuppressWarnings("javadoc")
public interface TrackSetter {

    void addFeature(Artist artist);

    void addProducer(Artist producer);

    void clearFeatures();
    
    void clearProducers();
    
    void removeFeature(Artist artist);

    void removeProducer(Artist producer);

    void setAudioContent(byte[] audioContent);
    
    void setExplicit(boolean explicit);
    
    void setFeatures(Collection<Artist> features);
    
    void setLyrics(String lyrics);

    void setProducers(Collection<Artist> producers);

}
