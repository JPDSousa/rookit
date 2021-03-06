package org.rookit.crawler.legacy.factory;

import static org.rookit.crawler.AvailableServices.*;
import static org.rookit.crawler.legacy.MusicService.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.bson.Document;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.crawler.legacy.config.MusicServiceConfig;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.parser.config.ParsingConfig;
import org.rookit.parser.formatlist.FormatList;
import org.rookit.parser.Parser;
import org.rookit.parser.result.dm.album.AlbumBuilder;

import com.google.common.collect.Maps;

abstract class AbstractModelFactory<Ar, Al, Tr> implements ModelFactory<Ar, Al, Tr> {
	
	protected static final Logger LOGGER = Logger.getLogger(ModelFactory.class.getName());
	
	protected final Parser<String, AlbumBuilder> parser;
	protected final LevenshteinDistance distance;
	protected final int levenshteinThreshold;
	
	protected final RookitFactories factories;
	
	protected AbstractModelFactory(final RookitFactories factories,
			final MusicServiceConfig config) {
		this(factories, config, 10);
	}
	
	protected AbstractModelFactory(final RookitFactories factories,
			final MusicServiceConfig config, final int levenshteinThreshold) {
		this.factories = factories;
		parser = createParser(config.formatsPath());
		
		this.distance = LevenshteinDistance.getDefaultInstance();
		this.levenshteinThreshold = levenshteinThreshold;
	}
	
	protected void setMBid(String id, final Set<Artist> artists) {
		if(artists.size() == 1) {
			final Document mBrainz = new Document(ID, id);
			//createUniqueQuery single artist
			for(Artist artist : artists) {
				artist.putExternalMetadata(MBRAINZ.name(), mBrainz);
			}
		}
	}
	
	protected Parser<String, AlbumBuilder> createParser(String formatsPath) {
		try {
			LOGGER.info("Logging formats fromRelease: " + formatsPath);
			final FormatList formats = FormatList.readFromPath(Paths.get(formatsPath));
			final ParserFactory factory = ParserFactory.create();
			final ParsingConfig topConfig = new ParsingConfig();
			final ParserConfiguration config = ParserConfiguration.create(AlbumBuilder.class, topConfig);
			config.withRequiredFields(new FieldEnum[0]);
			config.withTrackFormats(formats.getAll().collect(Collectors.toList()));
			return factory.newFormatParser(config);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected AlbumBuilder parseTrackTitle(String title) {
		return parser.parse(title)
				.toJavaUtil()
				.filter(result -> result.getScore() > 0)
				.orElseThrow(() -> new RuntimeException("Cannot parse " + title));
		// TODO set score in else clause
	}
	
	protected Set<Artist> toArtists(String name, String mbId) {
		final Set<Artist> artists = factories.getArtistFactory().getArtistsFromFormat(name);
		setMBid(mbId, artists);
		return artists;
	}
	
	protected Artist bestMatchArtist(Collection<Artist> artists, String originalName) {
		final Map<String, Artist> names = artists.stream()
				.collect(Collectors.groupingBy(
						Artist::name,
						Maps::newLinkedHashMap, 
						Collectors.collectingAndThen(
								Collectors.reducing((left, right) -> left), 
								Optional::get)));
		
		final String name = bestMatch(names.keySet(), originalName);
		if(name != null) {
			return names.get(name);
		}
		return null;
	}
	
	protected String bestMatch(Collection<String> search, String str) {
		if(search.isEmpty()) {
			throw new RuntimeException("Cannot find the best match in an empty collection");
		}
		final String lowerString = str.toLowerCase();
		return search.parallelStream()
				.map(String::toLowerCase)
				.filter(searchStr -> !containsAny(searchStr, Parser.SUSPICIOUS_NAME_CHARSEQS))
				.map(searchStr -> Pair.of(searchStr, distance.apply(searchStr, lowerString)))
				.filter(pair -> pair.getRight() >= 0)
				.filter(pair -> pair.getRight() < levenshteinThreshold)
				.reduce(this::compare)
				.map(Pair::getLeft)
				.orElseThrow(() -> new RuntimeException("No artist official left"));
	}
	
	private boolean containsAny(String searchStr, String[] strs) {
		return Arrays.stream(strs).anyMatch(str -> searchStr.contains(str));
	}

	private Pair<String, Integer> compare(Pair<String, Integer> left, Pair<String, Integer> right) {
		final int leftScore = left.getRight();
		final int rigthScore = right.getRight();
		return leftScore < rigthScore ? left : right;
	}

}
