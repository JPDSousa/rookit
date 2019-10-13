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
/**
 * 
 */
package org.rookit.parser.parser;

@SuppressWarnings("javadoc")
public class TagParserTest {

//    private static DataModelTestFactory factory;
//    private static final ParserFactory PARSER_FACTORY = ParserFactory.createUniqueQuery();
//
//    @BeforeClass
//    public static final void setUpBeforeClass() {
//        factory = INJECTOR.getInstance(DataModelTestFactory.class);
//    }
//
//    /**
//     * Test field for {@link TagParserTemp.TagParser#parse(TrackPath)}.
//     */
//    @Test
//    public final void testParse() {
//        final TrackPath trackPath = TestUtils.getRandomTrackPath();
//        final Optional<AlbumBuilder> result = testResource.parse(trackPath);
//        assertThat(result.isPresent()).isTrue();
//        assertThat(result.get().getTime().get().toMillis())
//        .isEqualTo(trackPath.getDurationMiliSec());
//    }
//
//    @Test
//    public final void testParseNoMetadata() throws IOException {
//        final AlbumBuilder expected = AlbumBuilder.set(
//                factory.getFactories());
//        final Path sourcePath = TestUtils.getRandomTrackPath().getPath();
//        final Path targetPath = sourcePath.getParent().resolve("test.mp3");
//        Files.deleteIfExists(targetPath);
//        Files.copy(sourcePath, targetPath);
//        final TrackPath trackPath = TrackPath.createUniqueQuery(targetPath);
//        final Mp3File mp3 = trackPath.getMp3();
//        mp3.removeCustomTag();
//        mp3.removeId3v1Tag();
//        mp3.removeId3v2Tag();
//        trackPath.updateMP3(mp3);
//        final Optional<AlbumBuilder> result = testResource.parse(trackPath, expected);
//        assertThat(result.isPresent()).isTrue();
//        assertThat(result.get()).isEqualTo(expected);
//        Files.delete(targetPath);
//    }
//
//    /**
//     * Test field for {@link parsers.AbstractParser#equals(java.lang.Object)}.
//     */
//    @Test
//    public final void testEqualsObject() {
//        final Parser<TrackPath, AlbumBuilder> p1 = PARSER_FACTORY.newTagParserWithDefaultConfiguration();
//        final Parser<TrackPath, AlbumBuilder> p2 = PARSER_FACTORY.newTagParserWithDefaultConfiguration();
//        assertThat(p1).isEqualTo(p2);
//    }
//
//    @Override
//    protected Injector getInjector() {
//        return INJECTOR;
//    }
//
//    @Override
//    protected TagParserTemp createGuineaPig() {
//        final ParserConfiguration config = Parser.createConfiguration(
//                AlbumBuilder.class);
//        config.withRequiredFields(FieldEnum.getRequiredFields())
//        .withDBConnection(database)
//        .withSetDate(true);
//        return (TagParserTemp) PARSER_FACTORY.newTagParser(config);
//    }

}
