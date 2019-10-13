package org.rookit.crawler.legacy;

import java.io.Closeable;
import java.util.Optional;

@SuppressWarnings("javadoc")
public interface ServiceProvider extends Closeable {
	
	Optional<MusicService> getService(AvailableServices serviceKey);

}
