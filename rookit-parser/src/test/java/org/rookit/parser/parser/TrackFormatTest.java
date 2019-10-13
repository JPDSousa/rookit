/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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
package org.rookit.parser.parser;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.parser.exceptions.AmbiguousFormatException;
import org.rookit.parser.exceptions.InvalidFieldException;
import org.rookit.parser.format.TrackFormatImpl;


@SuppressWarnings("javadoc")
public class TrackFormatTest {

	private static final Random RANDOM = new Random();
	private static final String[] FORMATS = {"<ARTIST> - <TITLES> X <EXTRA> <VERSION> [<IGNORE>]",
			"<ARTIST> - <TITLES> (<GENRE>)",
			"<ARTIST> feat. <FEAT> (<EXTRA> <VERSION>) [<IGNORE>] - <TITLES>",
			"feat. <FEAT> (<IGNORE>) - <TITLES>"
	};
	
	private TrackFormatImpl guineaPig;
	
	@Before
	public void setUp() {
		final int length = FORMATS.length;
		this.guineaPig = TrackFormatImpl.create(FORMATS[RANDOM.nextInt(length)]);
	}

	@Test
	public final void testTrackFormatString() {
		assertThat(guineaPig).isNotNull();
	}

	@Test
	public final void testToString() {
		final TrackFormatImpl clone = TrackFormatImpl.create(guineaPig.toString());
		assertThat(clone.toString()).isEqualTo(guineaPig.toString());
	}

	@Test
	public final void testGetFormats() {
		final List<FieldEnum> fields = Arrays.asList(FieldEnum.ARTIST, FieldEnum.TITLES, FieldEnum.GENRE);
		guineaPig = TrackFormatImpl.create(FORMATS[1]);
		assertThat(fields).isEqualTo(guineaPig.getFields());
	}

	@Test
	public final void testGetSeparators() {
		final List<String> seps = Arrays.asList(TrackFormatImpl.SEP_START, " - ", " (", ")");
		guineaPig = TrackFormatImpl.create(FORMATS[1]);
		assertThat(guineaPig.getSeparators()).isEqualTo(seps);
	}

	@Test
	public final void testStartsWithField() {
		List<String> seps;
		guineaPig = TrackFormatImpl.create(FORMATS[0]);
		seps = guineaPig.getSeparators();
		assertThat(seps.get(0)).isEqualTo(TrackFormatImpl.SEP_START);
		guineaPig = TrackFormatImpl.create(FORMATS[3]);
		seps = guineaPig.getSeparators();
		assertThat(seps.get(0)).isEqualTo("feat. ");
	}

	@Test
	public final void testEndsWithField() {
		List<String> seps;
		guineaPig = TrackFormatImpl.create(FORMATS[0]);
		seps = guineaPig.getSeparators();
		assertThat(seps.get(seps.size()-1)).isEqualTo("]");
		guineaPig = TrackFormatImpl.create(FORMATS[3]);
		seps = guineaPig.getSeparators();
		assertThat(seps.get(seps.size()-1)).isEqualTo(TrackFormatImpl.SEP_END);
	}

	@Test
	public final void testGetTrackClass() {
		guineaPig = TrackFormatImpl.create(FORMATS[0]);
		assertThat(guineaPig.getTrackClass()).isEqualTo(TypeTrack.VERSION);
		guineaPig = TrackFormatImpl.create(FORMATS[1]);
		assertThat(guineaPig.getTrackClass()).isEqualTo(TypeTrack.ORIGINAL);
	}

	@Test
	public final void testAppendSepToLastField() {
		final String sep = "sep";
		final int len;
		guineaPig = TrackFormatImpl.create(FORMATS[2]);
		len = guineaPig.getSeparators().size()-1;
		guineaPig.appendSep(sep);
		assertThat(guineaPig.getSeparators().get(len)).isEqualTo(sep);
	}
	
	@Test
	public final void testAppendSepToLastSep() {
		final String sep = "sep";
		final String lastSep;
		final int len;
		guineaPig = TrackFormatImpl.create(FORMATS[0]);
		len = guineaPig.getSeparators().size();
		lastSep = guineaPig.getSeparators().get(len-1);
		guineaPig.appendSep(sep);
		assertThat(guineaPig.getSeparators().get(len-1)).isEqualTo(lastSep+sep);
	}

	@Test
	public final void testAppendFieldToLastSep() {
		final FieldEnum field = FieldEnum.ALBUM;
		final int len;
		guineaPig = TrackFormatImpl.create(FORMATS[0]);
		len = guineaPig.getFields().size();
		guineaPig.appendField(field);
		assertThat(guineaPig.getFields().get(len)).isEqualTo(field);
	}
	
	@Test(expected = AmbiguousFormatException.class)
	public final void testAppendFieldToLastField() {
		guineaPig = TrackFormatImpl.create(FORMATS[2]);
		guineaPig.appendField(FieldEnum.ARTIST);
	}

	@Test
	public final void testFits() {
		final String ex = "a - b (c)";
		guineaPig = TrackFormatImpl.create(FORMATS[1]);
		assertThat(guineaPig.fits(ex)).isTrue();
		assertThat(guineaPig.fits(ex.substring(3))).isFalse();
	}

	@Test
	public final void testGetMissingRequiredFields() {
		final FieldEnum[] required = {FieldEnum.ARTIST, FieldEnum.IGNORE};
		guineaPig = TrackFormatImpl.create(FORMATS[1]);
		assertThat(guineaPig.getMissingRequiredFields(required)).isEqualTo(Arrays.asList(FieldEnum.IGNORE));
	}

	@Test
	public final void testEqualsObject() {
		final TrackFormatImpl f1 = TrackFormatImpl.create(FORMATS[2]);
		final TrackFormatImpl f2 = TrackFormatImpl.create(FORMATS[2]);
		assertThat(f1).isEqualTo(f2);
	}

	@Test
	public final void testCompareTo() {
		final TrackFormatImpl f1 = TrackFormatImpl.create(FORMATS[0]);
		final TrackFormatImpl f2 = TrackFormatImpl.create(FORMATS[1]);
		assertThat(f2.compareTo(f1) < 0).isTrue();
		assertThat(f2.compareTo(f2) == 0).isTrue();
		assertThat(f1.compareTo(f2) > 0).isTrue();
	}
	
	@Test(expected = InvalidFieldException.class)
	public void testContructorInvalidField() {
		TrackFormatImpl.create("<This FieldEnum should never exist>");
	}

	@Test(expected = InvalidFieldException.class)
	public void testConstructorEmptyField(){
		TrackFormatImpl.create("<>");
	}

	@Test(expected = AmbiguousFormatException.class)
	public void testConstructorAmbiguousFormat(){
		TrackFormatImpl.create(String.format("---<%s><%s>eolksdlfkds", FieldEnum.EXTRA.name(), FieldEnum.ARTIST.name()));
	}

	@Test
	public void testConstructor() {
		for(FieldEnum f : FieldEnum.values()){
			TrackFormatImpl.create(String.format("<%s>", f.name()));
		}
	}

	@Test
	public void testToStringFull(){
		final String[] testStrings = {FieldEnum.ALBUM.toString(),
				String.format("-a<%s>-r<%s>-ghj", FieldEnum.ARTIST.name(), FieldEnum.NUMBER.name())};
		final String field = FieldEnum.ARTIST.toString();
		assertThat(TrackFormatImpl.create(field).toString()).isEqualTo(field);
		for(String testString : testStrings){
			assertThat(TrackFormatImpl.create(testString).toString()).as("To string should return initial format string").isEqualTo(testString);
		}
	}
	
	@Test
	public void testAppend(){
		final String sepI = " -";
		final String sepII = " ";
		final String field = FieldEnum.ARTIST.toString();
		
		assertThat(TrackFormatImpl.create(field).appendSep(sepI).appendSep(sepII).getSeparators())
		.containsExactly(TrackFormatImpl.SEP_START, sepI+sepII);
		
		assertThat(TrackFormatImpl.create(field+sepI).appendSep(sepII).getSeparators())
		.containsExactly(TrackFormatImpl.SEP_START, sepI+sepII);
	}

}
