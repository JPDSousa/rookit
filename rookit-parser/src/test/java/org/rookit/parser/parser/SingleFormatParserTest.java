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

@SuppressWarnings("javadoc")
public class SingleFormatParserTest {

//    private static RookitFactories factories;
//
//    @BeforeClass
//    public static void setUpBeforeClass() {
//        factories = INJECTOR.getInstance(RookitFactories.class);
//    }
//
//    @Test
//    public final void testResult1() {
//        final TrackFormatImpl format = TrackFormatImpl.createUniqueQuery("<ARTIST_EXTRA> - <TITLES> <VERSION>");
//        final String input = "Dewian Gross - Free To Go";
//        testResource = createParser(input, format);
//        assertThat(testResource.parse(input).isPresent()).isFalse();
//    }
//
//    @Test
//    public final void testScore1() {
//        final String artist1 = "Artist1";
//        final String artist2 = "Artist2";
//        final String track1 = "Track1";
//        final String input = artist1 + " - " + track1 + " (feat. " + artist2 + ")";
//        final TrackFormatImpl format = TrackFormatImpl.createUniqueQuery("<ARTIST> - <TITLES> (feat. <FEAT>)");
//        final Parser<String, AlbumBuilder> parser = createParser(input, format);
//        final Map<FieldEnum, String> values = Maps.newLinkedHashMap();
//        final Optional<AlbumBuilder> result = parser.parse(input);
//        values.put(FieldEnum.ARTIST, artist1);
//        values.put(FieldEnum.TITLES, track1);
//        values.put(FieldEnum.FEAT, artist2);
//        final int expected = Math.round(getScoreFromFields(parser, values)
//                * parser.getConfig().getTokenizerPercentage());
//
//        assertThat(result.toJavaUtil()).isNotEmpty();
//        assertThat(result.get().getScore()).isEqualTo(expected);
//    }
//
//    private int getScoreFromFields(final Parser<String, AlbumBuilder> parser,
//            final Map<FieldEnum, String> values) {
//        return values.keySet().stream()
//                .mapToInt(field -> field.getScore(values.get(field), parser.getConfig()))
//                .sum();
//    }
//
//    private SingleFormatParser createParser(String input, TrackFormatImpl format) {
//        final AlbumBuilder base = AlbumBuilder.set(factories);
//        return new SingleFormatParser(format, input, base, testResource);
//    }
//
//    @Override
//    protected Injector getInjector() {
//        // TODO Auto-generated field stub
//        return null;
//    }
//
//    @Override
//    protected SingleFormatParser createGuineaPig() {
//        final ParserConfiguration config = Parser.createConfiguration(
//                AlbumBuilder.class);
//        try {
//            config.withRequiredFields(FieldEnum.getRequiredFields())
//            .withDBConnection(database)
//            .withSetDate(true)
//            .withTrackFormats(TestUtils.getTestFormats());
//        } catch (IOException e) {
//            return VALIDATOR.handleException(e);
//        }
//
//        return (SingleFormatParser) ParserFactory.createUniqueQuery().newFormatParser(config);
//    }

}
