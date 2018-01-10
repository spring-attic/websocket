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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.websocket.IntegrationWebSocketContainer;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter;

/**
 * A source module that receives data over WebSocket
 *
 * @author Krishnaprasad A S
 *
 */
@Configuration
@EnableConfigurationProperties(WebsocketSourceProperties.class)
@EnableBinding(Source.class)
public class WebsocketSourceConfiguration {

	@Autowired
	private WebsocketSourceProperties properties;

	@Bean
	public WebSocketInboundChannelAdapter webSocketInboundChannelAdapter() {
		WebSocketInboundChannelAdapter webSocketInboundChannelAdapter = new WebSocketInboundChannelAdapter(
				serverWebSocketContainer());
		webSocketInboundChannelAdapter.setOutputChannelName(Source.OUTPUT);
		return webSocketInboundChannelAdapter;
	}

	@Bean
	public IntegrationWebSocketContainer serverWebSocketContainer() {
		return new ServerWebSocketContainer(properties.getPath()).setAllowedOrigins(properties.getAllowedOrigins());
	}

}
