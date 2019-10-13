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
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rookit.parser.Parser;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.parser.format.TrackFormatImpl;
import org.rookit.parser.result.dm.album.AlbumBuilder;

@SuppressWarnings("javadoc")
public class ParserConfigurationTest {

	private static ParserConfiguration config;
	
	@Before
	public void setUp() {
		config = Parser.createConfiguration(AlbumBuilder.class);
	}
	
	@Test
	public final void testDBConnection() {
		final StorageManager db = mock(StorageManager.class);
		config.withDBConnection(db);
		assertThat(config.getDBConnection()).isEqualTo(db);
	}
	
	@Test
	public final void testSetDate() {
		final boolean date = true;
		config.withSetDate(date);
		assertThat(config.isSetDate()).isEqualTo(date);
	}
	
	@Test
	public final void testRequiredFields() {
		final FieldEnum[] fields = FieldEnum.getRequiredFields();
		config.withRequiredFields(fields);
		assertThat(config.getRequiredFields()).isEqualTo(fields);
		//test null fields (allowed)
		config.withRequiredFields(null);
	}
	
	@Test
	public final void testTrackFormats() {
		final List<TrackFormatImpl> formats = new ArrayList<>();
		config.withTrackFormats(formats);
		assertThat(config.getFormats()).isEqualTo(formats);
		//test null formats (allowed)
		config.withTrackFormats(null);
	}

}
