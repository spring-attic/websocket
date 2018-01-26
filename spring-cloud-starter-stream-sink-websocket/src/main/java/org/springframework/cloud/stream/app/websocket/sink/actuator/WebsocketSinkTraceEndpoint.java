/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.stream.app.websocket.sink.actuator;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.app.websocket.sink.trace.InMemoryTraceRepository;
import org.springframework.cloud.stream.app.websocket.sink.trace.Trace;

/**
 * Simple Spring Boot Actuator {@link Endpoint} implementation that
 * provides access to Websocket messages last sent/received
 *
 * @author Oliver Moser
 * @author Artem Bilan
 */
@ConfigurationProperties(prefix = "endpoints.websocketsinktrace")
@Endpoint(id = "websocketsinktrace")
public class WebsocketSinkTraceEndpoint {

	private static final Log logger = LogFactory.getLog(WebsocketSinkTraceEndpoint.class);

	private boolean enabled;

	private final InMemoryTraceRepository repository;

	public WebsocketSinkTraceEndpoint(InMemoryTraceRepository repository) {
		this.repository = repository;
		logger.info(String.format("/websocketsinktrace enabled: %b", this.enabled));
	}

	@PostConstruct
	public void init() {

	}

	@ReadOperation
	public List<Trace> traces() {
		return this.repository.findAll();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
