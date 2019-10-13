package org.rookit.crawler.legacy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.crawler.legacy.config.MusicServiceConfig;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import static org.rookit.crawler.AvailableServices.*;

class ServiceProviderImpl implements ServiceProvider {

	private final DB cache; 
	private final Map<AvailableServices, MusicService> activeServices;

	@Inject
	private ServiceProviderImpl(final RookitFactories factories, final MusicServiceConfig config) {
		cache = DBMaker.fileDB(new File(config.cachePath()))
				.fileMmapEnable()
				.make();
		activeServices = Maps.newHashMapWithExpectedSize(AvailableServices.values().length);
		//			activeServices.put(LASTFM, new LastFM(config.lastfm()));
		activeServices.put(SPOTIFY, new Spotify(factories, config, cache));
	}

	@Override
	public Optional<MusicService> getService(AvailableServices serviceKey) {
		return Optional.ofNullable(activeServices.get(serviceKey));
	}

	@Override
	public void close() throws IOException {
		cache.close();
	}

}
