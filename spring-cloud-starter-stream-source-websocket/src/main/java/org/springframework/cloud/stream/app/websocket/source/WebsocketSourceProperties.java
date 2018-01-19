/*
 * Copyright 2014-2018 the original author or authors.
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:

 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.stream.app.websocket.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties available for {@link WebsocketSourceConfiguration}
 *
 * @author Krishnaprasad A S
 *
 */
@ConfigurationProperties("websocket")
public class WebsocketSourceProperties {

	public static final String DEFAULT_PATH = "/websocket";

	private static final String DEFAULT_ALLOWED_ORIGINS = "*";

	/**
	 * The path on which server WebSocket handler is exposed. Default is
	 * <tt>/websocket</tt>
	 */
	String path = DEFAULT_PATH;

	/**
	 * The allowed origins. Default is <tt>*</tt>
	 */
	String allowedOrigins = DEFAULT_ALLOWED_ORIGINS;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

}
