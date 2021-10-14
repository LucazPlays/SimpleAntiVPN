package de.luca.simpleantivpn.utils;

public class RestAPIResponse {

	private String text;

	private boolean failed;

	private String url;
	
	public RestAPIResponse(String text,boolean failed, String url) {
		this.url = url;
		this.text = text;
		this.failed = failed;
	}


	public String getText() {
		return text;
	}

	public boolean getFailed() {
		return failed;
	}


	public String getUrl() {
		return url;
	}
}
