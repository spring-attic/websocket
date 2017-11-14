package org.springframework.cloud.stream.app.websocket.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Command line arguments available for {@ WebsocketSourceConfiguration}
 *
 * @author krishnaprasad
 *
 */
@ConfigurationProperties("websocket")
public class WebsocketSourceProperties {

	public static final String DEFAULT_PATH = "/websocket";

	/**
	 * the path on which a WebsocketSource consumer needs to connect. Default is
	 * <tt>/websocket</tt>
	 */
	String path = DEFAULT_PATH;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
