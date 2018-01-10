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
 * @author krishnaprasad
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
		return new ServerWebSocketContainer(properties.getPath()).setAllowedOrigins("*");
	}

}