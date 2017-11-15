package org.springframework.cloud.stream.app.websocket.source;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.websocket.ClientWebSocketContainer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * The WebSocketSourceIntegrationTests class
 *
 * @author krishnaprasad
 *
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WebsocketSourceConfiguration.class,
		WebSocketSourceIntegrationTests.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
				"websocket.path=/some_websocket_path", "spring.cloud.stream.default-binder=kafka" })
public class WebSocketSourceIntegrationTests {

	@LocalServerPort
	private String port;

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		return factory;
	}

	@Test
	public void testWebSocketStreamSource() throws IOException, InterruptedException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
		ClientWebSocketContainer clientWebSocketContainer = new ClientWebSocketContainer(webSocketClient,
				"ws://localhost:" + port + "/some_websocket_path");
		clientWebSocketContainer.start();
		WebSocketSession session = clientWebSocketContainer.getSession(null);
		session.sendMessage(new TextMessage("foo"));
	}

}*/