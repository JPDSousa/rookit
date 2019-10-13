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
public class MultiExpressionFieldParserTest {

//    private static RookitFactories factories;
//    private static ParserFactory parserFactory;
//
//    private ParserConfiguration config;
//
//    @BeforeClass
//    public static void setUp() {
//        factories = INJECTOR.getInstance(RookitFactories.class);
//        parserFactory = ParserFactory.createUniqueQuery();
//    }
//
//    @Test
//    public final void testEquals() {
//        final Parser<String, AlbumBuilder> p1 = parserFactory.newFormatParserWithDefaultConfiguration();
//        final Parser<String, AlbumBuilder> p2 = parserFactory.newFormatParserWithDefaultConfiguration();
//        assertThat(p1).isEqualTo(p2);
//    }
//
//    private final void testMultiparse(String input) {
//        final Iterable<AlbumBuilder> results = testResource.parseAll(input);
//        assertThat(results).isNotNull();
//        System.out.println(input);
//        for(AlbumBuilder result : results) {
//            System.out.println(result.getFormat());
//            System.out.println("Score: " + result.getScore());
//            System.out.println(DataModelPrintUtils.track(result.buildTrack()));
//        }
//    }
//
//    @Test
//    public final void testMultiparse() {
//        testMultiparse("Artist1 - Track1 (feat. Artist2)");
//    }
//
//    @Test
//    public final void testMultiParse7() {
//        testMultiparse("A R I Z O N A - Electric Touch (Lyrics _ Lyric Video)");
//    }
//
//    @Test
//    public final void testParse() {
//        final TrackFormatImpl format = TrackFormatImpl.createUniqueQuery("<ARTIST> - <TITLE> (<IGNORE>)");
//        final String artist = "Zabreguelles";
//        final String title = "This Title Is Awesome";
//        final String ignore = "Ignore me";
//        final String str = String.format("%s - %s (%s)", artist, title, ignore);
//
//        testResource = (MultiFormatParser) parserFactory.newFormatParser(config.withTrackFormats(Arrays.asList(format)));
//        final Optional<AlbumBuilder> result = testResource
//                .parse(str, AlbumBuilder.set(factories));
//        assertThat(result.isPresent()).isTrue();
//        final Track track = result.get().buildTrack();
//        assertThat(Iterables.get(track.mainArtists(), 0).official())
//        .isEqualTo(artist);
//        assertThat(track.title().title()).isEqualTo(title);
//        assertThat(result.get().getIgnored().get(0)).isEqualTo(ignore);
//    }
//
//    @Test
//    public final void testCheckSong() throws InvalidSongFormatException {
//        testResource.checkSong(Paths.get("dir", "valid.mp3"));
//        assertThat(true).isTrue();
//    }
//
//    @Test(expected = InvalidSongFormatException.class)
//    public final void testCheckSongInvalidSongFormatException() throws InvalidSongFormatException {
//        testResource.checkSong(Paths.get("dir", "invalid.wrongformat"));
//        fail("Should have thrown exception");
//    }
//
//
//    @Test
//    public final void testAmbiguousFormat() {
//        final TrackFormatImpl format = TrackFormatImpl.createUniqueQuery("[<GENRE>] - <ARTIST> ft. <FEAT> - <TITLES> (<EXTRA> <VERSION>)");
//        final String ex = "[Progressive] -  Ale Q & Avedon Ft. Jonathan Mendelsohn -  Open My Eyes (Tom Swoon Edit)";
//        testResource = (MultiFormatParser) parserFactory.newFormatParser(config.withTrackFormats(Arrays.asList(format)));
//        final Optional<AlbumBuilder> result = testResource.parse(ex,
//                AlbumBuilder.set(factories));
//        System.out.println(DataModelPrintUtils.track(result.get().buildTrack()));
//    }
//
//    @Override
//    protected Injector getInjector() {
//        return INJECTOR;
//    }
//
//    @Override
//    protected MultiFormatParser createGuineaPig() {
//        try {
//            config = Parser.createConfiguration(AlbumBuilder.class)
//                    .withDBConnection(this.database)
//                    .withRequiredFields(FieldEnum.getRequiredFields())
//                    .withSetDate(true)
//                    .withTrackFormats(TestUtils.getTestFormats());
//
//            return (MultiFormatParser) parserFactory.newFormatParser(config);
//        } catch (IOException e) {
//            return VALIDATOR.handleException(e);
//        }
//    }

}
