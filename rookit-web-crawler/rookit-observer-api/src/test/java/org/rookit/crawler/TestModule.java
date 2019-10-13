package org.rookit.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.rookit.crawler.legacy.RookitCrawler;
import org.rookit.crawler.legacy.ServiceProvider;
import org.rookit.crawler.legacy.ServiceProviderImpl;
import org.rookit.crawler.legacy.config.MusicServiceConfig;
import org.rookit.crawler.legacy.config.SpotifyConfig;
import org.rookit.dm.similarity.SimilarityProvider;
import org.rookit.dm.similarity.SimilarityProviderImpl;

import com.google.inject.AbstractModule;

@SuppressWarnings("javadoc")
public class TestModule extends AbstractModule {

	private static final Path SECRET_PATH = Resources.RESOURCES_TEST.resolve("client").resolve("secret.txt");
	private static final Path FORMATS_PATH = Resources.RESOURCES_TEST.resolve("parser").resolve("formats.txt");
	
	private static final MusicServiceConfig readConfig() throws IOException {
		final BufferedReader reader = Files.newBufferedReader(SECRET_PATH);
		final MusicServiceConfig config = new MusicServiceConfig();
		final SpotifyConfig sConfig = new SpotifyConfig();
		
		sConfig.setClientId(reader.readLine());
		sConfig.setClientSecret(reader.readLine());
		config.setFormatsPath(FORMATS_PATH.toString());
		config.setSpotify(sConfig);
		reader.close();
		return config;
	}
	
	@Override
	protected void configure() {
		try {
			bind(MusicServiceConfig.class).toInstance(readConfig());
			bind(ServiceProvider.class).to(ServiceProviderImpl.class);
			bind(SimilarityProvider.class).to(SimilarityProviderImpl.class);
			bind(RookitCrawler.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
