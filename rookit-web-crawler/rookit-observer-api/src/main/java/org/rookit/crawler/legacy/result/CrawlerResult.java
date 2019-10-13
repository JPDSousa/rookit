package org.rookit.crawler.legacy.result;

@SuppressWarnings("javadoc")
public interface CrawlerResult<T> {

	static <T> CrawlerResult<T> create(AvailableServices service, T result) {
		return new CrawlerResultImpl<T>(service, result);
	}
	
	AvailableServices getService();
	
	T getResult();
}
