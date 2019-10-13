package org.rookit.crawler.legacy.result;

class CrawlerResultImpl<T> implements CrawlerResult<T> {

	private final AvailableServices service;
	private final T result;
	
	CrawlerResultImpl(AvailableServices service, T result) {
		super();
		this.service = service;
		this.result = result;
	}

	@Override
	public AvailableServices getService() {
		return service;
	}

	@Override
	public T getResult() {
		return result;
	}
}
