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
package org.rookit.api.dm.album;

import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.track.Track;
import org.rookit.io.data.DataBucket;

import java.nio.file.Files;
import java.time.LocalDate;

/**
 * An interface with the album setters.
 *
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 */
@SuppressWarnings("javadoc")
public interface AlbumSetter {

    void addTrack(Track track, int i, String discName);

    void addTrack(final TrackSlot slot);

    void addTrackLast(Track track, String discName);

    /**
     * <b>This field is only used by {@link Track} to relocate tracks when its
     * primitive and/or disc are changed. Do not use this field otherwise.</b>
     * 
     * This field relocates the track in the album's track list to the disc and
     * primitive passed as parameter.
     * 
     * @param discName
     *            source disc official
     * @param number
     *            source primitive
     * @param newDiscName
     *            target disc official
     * @param newNumber
     *            target track primitive
     * @return TODO
     * 
     */
    void relocate(String discName, int number, String newDiscName, int newNumber);

    void clearTracks();

    void removeTrack(int number, String disc);

    void removeTrack(Track track);

    /**
     * Sets a new cover art for the album. The cover art is represented as a
     * byte array so, in order to read from a JPEG file one should use
     * {@link Files#readAllBytes(java.nio.file.Path)} as so:
     * 
     * <pre>
     * Files.readAllBytes(Paths.get("jpeg-path-as-string");
     * </pre>
     * <p>
     * In order to download an image into a byte array:
     * </p>
     * 
     * <pre>
     * URL u = new URL("http://localhost:8080/images/anImage.jpg");
     * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
     * try {
     *     byte[] chunk = new byte[4096];
     *     int bytesRead;
     *     InputStream stream = toDownload.openStream();
     * 
     *     while ((bytesRead = stream.read(chunk)) > 0) {
     *         outputStream.write(chunk, 0, bytesRead);
     *     }
     * 
     * } catch (IOException e) {
     *     e.printStackTrace();
     * }
     * outputStream.toByteArray();// image bytes
     * </pre>
     * 
     * @param image
     *            byte array that represents the image to be withProperty as cover
     * @return object to return
     */
    void setCover(byte[] image);

    void setCover(DataBucket image);

    /**
     * Sets a new release date for the album. The old date assigned will be
     * overwritten.
     * 
     * @param date
     *            new date to be assigned
     * @return object to return
     */
    void setReleaseDate(LocalDate date);

}
