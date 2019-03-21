/*
 * Copyright 2018 the original author or authors.
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:

 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.stream.app.websocket.source;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.websocket.ClientWebSocketContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * Tests for Websocket Source
 *
 * @author Krishnaprasad A S
 * @author Artem Bilan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "websocket.path=/some_websocket_path")
@DirtiesContext
public class WebSocketSourceIntegrationTests {

	@Autowired
	private WebsocketSourceProperties properties;

	@Autowired
	protected Source channels;

	@Autowired
	protected MessageCollector messageCollector;

	@Autowired
	private SecurityProperties securityProperties;

	@LocalServerPort
	private String port;

	private final String messageString = "foo";

	@Test
	public void checkCmdlineArgs() {
		assertThat(this.properties.getPath(), is("/some_websocket_path"));
		assertThat(this.properties.getAllowedOrigins(), is("*"));
	}

	@Test
	public void testWebSocketStreamSource() throws IOException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
		ClientWebSocketContainer clientWebSocketContainer =
				new ClientWebSocketContainer(webSocketClient, "ws://localhost:{port}{path}",
						this.port,
						this.properties.getPath());

		HttpHeaders httpHeaders = new HttpHeaders();

		String token = Base64Utils.encodeToString(
				(this.securityProperties.getUser().getName() + ":" + this.securityProperties.getUser().getPassword())
						.getBytes(StandardCharsets.UTF_8));
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Basic " + token);

		clientWebSocketContainer.setHeaders(httpHeaders);

		clientWebSocketContainer.start();

		WebSocketSession session = clientWebSocketContainer.getSession(null);
		session.sendMessage(new TextMessage(this.messageString));
		assertThat(this.messageCollector.forChannel(this.channels.output()),
				receivesPayloadThat(is(messageString)));
		session.close();
	}

	@SpringBootApplication
	public static class WebsocketSourceApplication {

	}

}
