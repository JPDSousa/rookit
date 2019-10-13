package org.rookit.core.config;

import static org.assertj.core.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;

@SuppressWarnings("javadoc")
public class ConfigTest {
	
	private static final Path CONFIG_DIR = Resources.RESOURCES_TEST.resolve("config");
	private static final Path CONFIG_DUMMY_PATH = CONFIG_DIR.resolve("config.json");
	private static final Path CONFIG_EMPTY_PATH = CONFIG_DIR.resolve("empty.json");
	
	
	private static final String DRIVER_NAME = "rookit-monogdb";
	private static final int PARSER_LIMIT = 5;
	
	@Before
	public final void beforeTest() throws IOException {
		final FileWriter fileWriter = new FileWriter(CONFIG_DUMMY_PATH.toFile());
		final JsonWriter writer = new JsonWriter(fileWriter);
		writer.beginObject()
		.name("database").beginObject()
			.name("driverName").value(DRIVER_NAME)
			.endObject()
		.name("parser").beginObject()
			.name("resultsLimit").value(PARSER_LIMIT)
			.endObject()
		.endObject();
		writer.close();
		fileWriter.close();
	}

	@Test
	public final void testReadPath() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		final Config config = Config.read(CONFIG_DUMMY_PATH);
		assertThat(config.getDatabase().getDriverName()).isEqualTo(DRIVER_NAME);
		assertThat(config.getParsing().getParserLimit()).isEqualTo(PARSER_LIMIT);
	}

	@Test
	public final void testUpdateConfigPath() throws JsonSyntaxException, JsonIOException, IOException {
		final Config config = Config.read(CONFIG_DUMMY_PATH);
		final int expected = 10;
		config.getParsing().setParserLimit(expected);
		Config.update(config, CONFIG_DUMMY_PATH);
		final int actual = Config.read(CONFIG_DUMMY_PATH).getParsing().getParserLimit();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public final void testDefaults() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		final Config config = Config.read(CONFIG_EMPTY_PATH);
		assertThat(config).isNotNull();
		final DatabaseConfig dbConfig = config.getDatabase();
		final ParsingConfig parseConfig = config.getParsing();
		assertThat(dbConfig).isNotNull();
		assertThat(parseConfig).isNotNull();
		assertThat(dbConfig.getDriverName()).isNotNull();
		assertThat(dbConfig.getOptions()).isNotNull();
		assertThat(parseConfig.getParserLimit() > 0).isTrue();
	}

}
