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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.websocket.ClientWebSocketContainer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * Tests for Websocket Source
 *
 * @author Krishnaprasad A S
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WebsocketSourceConfiguration.class,
		WebSocketSourceIntegrationTests.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
				"websocket.path=/some_websocket_path", "websocket.allowedOrigins=*",
				"spring.cloud.stream.default-binder=kafka" })
@EnableAutoConfiguration
@DirtiesContext
public class WebSocketSourceIntegrationTests {

	@Autowired
	private WebsocketSourceProperties properties;

	@Autowired
	protected Source channels;

	@Autowired
	protected MessageCollector messageCollector;

	@LocalServerPort
	private String port;

	private String messageString = "foo";

	@Test
	public void checkCmdlineArgs() {
		assertThat(properties.getPath(), is("/some_websocket_path"));
		assertThat(properties.getAllowedOrigins(), is("*"));
	}

	@Test
	public void testWebSocketStreamSource() throws IOException, InterruptedException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
		ClientWebSocketContainer clientWebSocketContainer = new ClientWebSocketContainer(webSocketClient,
				"ws://localhost:" + port + "/" + properties.path);
		clientWebSocketContainer.start();
		WebSocketSession session = clientWebSocketContainer.getSession(null);
		session.sendMessage(new TextMessage(messageString));
		assertThat(this.messageCollector.forChannel(channels.output()), is(messageString));
		session.close();
	}

}