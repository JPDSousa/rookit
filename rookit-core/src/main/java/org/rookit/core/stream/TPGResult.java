package org.rookit.core.stream;

import java.util.List;

import org.rookit.parser.result.dm.album.AlbumBuilder;
import org.rookit.parser.utils.TrackPath;

import com.google.common.collect.Lists;

@SuppressWarnings("javadoc")
public class TPGResult {
	
	private final TrackPath source;
	private final List<AlbumBuilder> results;
	
	public TPGResult(TrackPath source, Iterable<AlbumBuilder> results) {
		super();
		this.source = source;
		this.results = Lists.newArrayList(results);
	}

	public TrackPath getSource() {
		return source;
	}

	public List<AlbumBuilder> getResults() {
		return results;
	}

}
